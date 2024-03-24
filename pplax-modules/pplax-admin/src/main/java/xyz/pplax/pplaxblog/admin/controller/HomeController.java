package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.DateUtil;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.CommentSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.UserSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;
import xyz.pplax.pplaxblog.xo.service.comment.CommentService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;

import java.util.*;

/**
 * 首页 Controller
 */
@RestController
@RequestMapping("/home")
public class HomeController extends SuperController {

    private static final Logger log = LogManager.getLogger(HomeController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @ApiOperation(value="初始化", notes="初始化")
    @GetMapping("/init")
    public String init() {
        Map<String, Object> dataMap = new HashMap<>();

        /*
         * 封装统计数据
         */
        // 获取博客数量
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        int blogTotal = blogService.count(blogQueryWrapper);

        // 获取当天访问量
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        String start = DateUtil.getToDayStartTime();
        String end = DateUtil.getToDayEndTime();
        userQueryWrapper.ge(UserSQLConstants.LAST_LOGIN_TIME, start);
        userQueryWrapper.lt(UserSQLConstants.LAST_LOGIN_TIME, end);
        int visitAddTotal = userService.count(userQueryWrapper);

        // 获取用户数
        userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ne(UserSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        int userTotal = userService.count(userQueryWrapper);

        // 获取评论数
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.ne(CommentSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        int commentTotal = commentService.count(commentQueryWrapper);

        Map<String, Integer> statMap = new HashMap<>();
        statMap.put("blogTotal", blogTotal);
        statMap.put("visitAddTotal", visitAddTotal);
        statMap.put("userTotal", userTotal);
        statMap.put("commentTotal", commentTotal);
        dataMap.put("statistics", statMap);

        /*
         * 封装阅读量前五的文章
         */
        blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        blogQueryWrapper.orderByDesc(BlogSQLConstants.CLICK_COUNT);
        Page<Blog> page = new Page<>();
        page.setCurrent(1);
        page.setSize(5);
        IPage<Blog> blogIPage = blogService.page(page, blogQueryWrapper);
        dataMap.put("blogList", blogIPage.getRecords());



        return success(dataMap);
    }

}

