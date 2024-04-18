package xyz.pplax.pplaxblog.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.feign.AdminFeignClient;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogEditDto;
import xyz.pplax.pplaxblog.xo.dto.edit.CommentEditDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.Collect;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;
import xyz.pplax.pplaxblog.xo.service.collect.CollectService;
import xyz.pplax.pplaxblog.xo.service.comment.CommentService;
import xyz.pplax.pplaxblog.xo.service.site.SiteService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 博客 Controller
 */
@RestController
@RequestMapping("/blog")
@Api(value="博客Controller", tags={"博客Controller"})
public class BlogController extends SuperController {

    private static Logger log = LogManager.getLogger(BlogController.class);

    @Autowired
    private AdminFeignClient adminFeignClient;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private UserService userService;


    @ApiOperation(value = "获取博客列表", httpMethod = "GET", response = ResponseResult.class, notes = "网站相关信息")
    @GetMapping("/list")
    public String getBlogList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "blogSortUid", required = false) String blogSortUid,
            @RequestParam(value = "tagUid", required = false) String tagUid,
            @RequestParam(value = "orderByDesc", required = false) String orderByDesc,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){
        IPage<Blog> blogIPage = blogService.listHomeBlog(blogSortUid, tagUid, orderByDesc, currentPage, pageSize);
        return toJson(ResponseResult.success(blogIPage.getRecords(), blogIPage.getTotal()));
    }

    @ApiOperation(value = "获取博客", httpMethod = "GET", response = ResponseResult.class, notes = "获取博客")
    @GetMapping("/{blogUid}")
    public String getBlog(
            HttpServletRequest httpServletRequest,
            @PathVariable(value = "blogUid", required = false) String blogUid
    ){
        String userUid = null;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            String accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        return success(blogService.getByIdWithAll(blogUid, userUid));
    }

    @ApiOperation(value = "博客点赞", httpMethod = "POST", response = ResponseResult.class, notes = "博客点赞")
    @PostMapping("/{blogUid}/like")
    public String like(
            HttpServletRequest httpServletRequest,
            @PathVariable(value = "blogUid") String blogUid
    ){
        String userUid = null;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            String accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        boolean res = commentService.like(blogUid, userUid, CharacterConstants.NUM_ONE);

        if (res) {
            return success();
        }

        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value = "收藏", httpMethod = "POST", response = ResponseResult.class, notes = "收藏")
    @PostMapping("/{blogUid}/collect")
    public String collect(
            HttpServletRequest httpServletRequest,
            @PathVariable(value = "blogUid") String blogUid
    ){
        String userUid = null;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            String accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        Collect collect = new Collect();
        collect.setUid(StringUtils.getUUID());
        collect.setBlogUid(blogUid);
        collect.setUserUid(userUid);

        boolean res = collectService.save(blogUid, userUid);

        if (res) {
            return success();
        }

        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @ApiOperation(value = "删除", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除")
    @DeleteMapping("/{blogUid}")
    public String delete(@PathVariable(value = "blogUid") String blogUid){
        return adminFeignClient.deleteBlog(blogUid);
    }


    @ApiOperation(value = "获取评论列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取评论列表")
    @GetMapping("/{blogUid}/comment/list")
    public String getBlogList(
            @PathVariable(value = "blogUid") String blogUid,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){

        IPage<Comment> commentIPage = commentService.pageByBlogUid(blogUid, 0, currentPage, pageSize);

        return toJson(ResponseResult.success(commentIPage.getRecords(), commentIPage.getTotal()));
    }

    @ApiOperation(value = "评论", httpMethod = "POST", response = ResponseResult.class, notes = "评论")
    @PostMapping("/{blogUid}/comment")
    public String comment(
            HttpServletRequest httpServletRequest,
            @PathVariable("blogUid") String blogUid,
            @RequestBody CommentEditDto commentEditDto
    ){
        String userUid = null;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            String accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        Comment comment = new Comment();
        comment.setContent(commentEditDto.getContent());
        comment.setOriginalUid(blogUid);
        comment.setType(0);
        comment.setUid(StringUtils.getUUID());
        comment.setUserUid(userUid);
        comment.setIp(IpUtils.getIpAddress(httpServletRequest));
        comment.setAddress(IpUtils.getCityInfo(comment.getIp()));

        boolean res = commentService.save(comment);

        if (res) {
            return success();
        }

        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @ApiOperation(value="编辑博客", notes="编辑博客")
    @PutMapping("/{blogUid}")
    public String updateBlog(@PathVariable("blogUid") String blogUid, @RequestBody @Validated(value = {Update.class}) BlogEditDto blogEditDto) {
        blogEditDto.setUid(blogUid);
        Boolean res = blogService.updateById(blogEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @ApiOperation(value="添加博客", notes="添加博客")
    @PostMapping(value = "")
    public String add(HttpServletRequest httpServletRequest, @RequestBody @Validated(value = {Insert.class}) BlogEditDto blogEditDto) {
        String authorization = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        String payloadByBase64 = JwtUtil.getPayloadByBase64(authorization);
        String userUid = JSON.parseObject(payloadByBase64).get("uid").toString();
        blogEditDto.setUserUid(userUid);

        Blog blog = blogService.save(blogEditDto);

        if (blog != null) {
            return success(blog);
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}

