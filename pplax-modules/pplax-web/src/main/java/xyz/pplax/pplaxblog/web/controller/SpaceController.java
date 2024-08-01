package xyz.pplax.pplaxblog.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.service.BlogService;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;


/**
 * 个人主页 Controller
 */
@RestController
@RequestMapping("/user/{userUid}/space")
@Api(value="个人主页Controller", tags={"个人主页Controller"})
public class SpaceController extends SuperController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BlogService blogService;

    @ApiOperation(value="获取用户 数据", notes="获取用户 数据")
    @GetMapping(value = "/count")
    public ResponseResult getUserCount (@PathVariable("userUid") String userUid) {

        return success(userInfoService.getUserCount(userUid));
    }

    @ApiOperation(value = "获取博客列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取博客列表")
    @GetMapping("/blog/list")
    public ResponseResult getBlogList(
            @PathVariable("userUid") String userUid,
            @RequestParam(value = "isCollect") Boolean isCollect,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){

        Page<Blog> blogPage = blogService.pageByUserUid(userUid, isCollect, currentPage, pageSize);
        return ResponseResult.success(blogPage.getRecords(), blogPage.getTotal());
    }
}
