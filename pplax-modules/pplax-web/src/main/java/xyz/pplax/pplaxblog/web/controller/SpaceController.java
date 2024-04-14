package xyz.pplax.pplaxblog.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.feign.AdminFeignClient;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

import javax.servlet.http.HttpServletRequest;


/**
 * 个人主页 Controller
 */
@RestController
@RequestMapping("/user/space")
@Api(value="个人主页Controller", tags={"个人主页Controller"})
public class SpaceController extends SuperController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BlogService blogService;

    @ApiOperation(value="获取用户 数据", notes="获取用户 数据")
    @GetMapping(value = "/count")
    public String getUserCount (HttpServletRequest httpServletRequest) {
        String userUid = null;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            String accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        return success(userInfoService.getUserCount(userUid));
    }

    @ApiOperation(value = "获取博客列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取博客列表")
    @GetMapping("/blog/list")
    public String getBlogList(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "isCollect") Boolean isCollect,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){
        String userUid = null;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            String accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        IPage<Blog> blogIPage = blogService.listByUserUid(userUid, isCollect, currentPage, pageSize);
        return toJson(ResponseResult.success(blogIPage.getRecords(), blogIPage.getTotal()));
    }
}
