package xyz.pplax.pplaxblog.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogEditDto;
import xyz.pplax.pplaxblog.xo.dto.edit.CommentEditDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.Collect;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.service.BlogService;
import xyz.pplax.pplaxblog.xo.service.CollectService;
import xyz.pplax.pplaxblog.xo.service.CommentService;

import javax.servlet.http.HttpServletRequest;


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
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CollectService collectService;

    @ApiOperation(value = "查找", httpMethod = "GET", response = ResponseResult.class, notes = "查找")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword",value = "关键词",defaultValue = "",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/search")
    public ResponseResult search(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ){
        Page<Blog> blogPage = blogService.search(keyword, currentPage, pageSize);

        return ResponseResult.success(blogPage.getRecords(), blogPage.getTotal());
    }

    @ApiOperation(value = "获取博客列表", httpMethod = "GET", response = ResponseResult.class, notes = "网站相关信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogSortUid",value = "博客分类uid",defaultValue = "",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "tagUid",value = "标签uid",defaultValue = "",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "orderByDesc",value = "降序排序方式",defaultValue = "",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/list")
    public ResponseResult getBlogList(
            @RequestParam(value = "blogSortUid", required = false) String blogSortUid,
            @RequestParam(value = "tagUid", required = false) String tagUid,
            @RequestParam(value = "orderByDesc", required = false) String orderByDesc,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ){
        Page<Blog> blogPage = blogService.pageHomeBlog(blogSortUid, tagUid, orderByDesc, currentPage, pageSize);
        return ResponseResult.success(blogPage.getRecords(), blogPage.getTotal());
    }

    @ApiOperation(value = "获取博客", httpMethod = "GET", response = ResponseResult.class, notes = "获取博客")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogUid",value = "博客uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @GetMapping("/{blogUid}")
    public ResponseResult getBlog(
            HttpServletRequest httpServletRequest,
            @PathVariable(value = "blogUid", required = false) String blogUid
    ){
        String userUid = getUserUid(httpServletRequest);

        return success(blogService.getByIdWithAll(blogUid, userUid));
    }

    @ApiOperation(value = "博客点赞", httpMethod = "POST", response = ResponseResult.class, notes = "博客点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogUid",value = "博客uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @PostMapping("/{blogUid}/like")
    public ResponseResult like(
            HttpServletRequest httpServletRequest,
            @PathVariable(value = "blogUid") String blogUid
    ){
        String userUid = getUserUid(httpServletRequest);

        commentService.like(blogUid, userUid, CharacterConstants.NUM_ONE);

        return success();
    }

    @ApiOperation(value = "收藏", httpMethod = "POST", response = ResponseResult.class, notes = "收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogUid",value = "博客uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @PostMapping("/{blogUid}/collect")
    public ResponseResult collect(
            HttpServletRequest httpServletRequest,
            @PathVariable(value = "blogUid") String blogUid
    ){
        String userUid = getUserUid(httpServletRequest);

        Collect collect = new Collect();
        collect.setUid(StringUtils.getUUID());
        collect.setBlogUid(blogUid);
        collect.setUserUid(userUid);

        collectService.save(blogUid, userUid);

        return success();
    }


    @ApiOperation(value = "删除", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogUid",value = "博客uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{blogUid}")
    public String delete(@PathVariable(value = "blogUid") String blogUid){
        return adminFeignClient.deleteBlog(blogUid);
    }


    @ApiOperation(value = "获取评论列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogUid",value = "博客uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/{blogUid}/comment/list")
    public ResponseResult getBlogList(
            @PathVariable(value = "blogUid") String blogUid,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ){

        Page<Comment> commentIPage = commentService.pageByBlogUid(blogUid, 0, currentPage, pageSize);

        return ResponseResult.success(commentIPage.getRecords(), commentIPage.getTotal());
    }

    @ApiOperation(value = "评论", httpMethod = "POST", response = ResponseResult.class, notes = "评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogUid",value = "博客uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @PostMapping("/{blogUid}/comment")
    public ResponseResult comment(
            HttpServletRequest httpServletRequest,
            @PathVariable("blogUid") String blogUid,
            @RequestBody CommentEditDto commentEditDto
    ){
        String userUid = getUserUid(httpServletRequest);

        Comment comment = new Comment();
        comment.setContent(commentEditDto.getContent());
        comment.setOriginalUid(blogUid);
        comment.setType(0);
        comment.setUid(StringUtils.getUUID());
        comment.setUserUid(userUid);
        comment.setIp(IpUtils.getIpAddress(httpServletRequest));
        comment.setAddress(IpUtils.getCityInfo(comment.getIp()));

        rabbitTemplate.convertAndSend(MqConstants.EXCHANGE_DIRECT, MqConstants.PPLAX_COMMENT, comment);

        return success();
    }


    @ApiOperation(value="编辑博客", notes="编辑博客")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogUid",value = "博客uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @PutMapping("/{blogUid}")
    public ResponseResult updateBlog(HttpServletRequest httpServletRequest, @PathVariable("blogUid") String blogUid, @RequestBody @Validated(value = {Update.class}) BlogEditDto blogEditDto) {
        String userUid = getUserUid(httpServletRequest);
        blogEditDto.setUid(blogUid);
        blogService.userUpdateById(userUid, blogEditDto);

        return success();
    }


    @ApiOperation(value="添加博客", notes="添加博客")
    @PostMapping(value = "")
    public ResponseResult add(HttpServletRequest httpServletRequest, @RequestBody @Validated(value = {Insert.class}) BlogEditDto blogEditDto) {

        String userUid = getUserUid(httpServletRequest);
        blogEditDto.setUserUid(userUid);

        Blog blog = blogService.save(blogEditDto);

        if (blog != null) {
            return success(blog);
        }
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

