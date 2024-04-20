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
import xyz.pplax.pplaxblog.xo.constants.sql.*;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.service.BlogService;
import xyz.pplax.pplaxblog.xo.service.BlogSortService;
import xyz.pplax.pplaxblog.xo.service.CommentService;
import xyz.pplax.pplaxblog.xo.service.TagService;
import xyz.pplax.pplaxblog.xo.service.UserService;

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
    private BlogSortService blogSortService;

    @Autowired
    private TagService tagService;

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

        /*
         * 封装文章分类相关数据
         */
        QueryWrapper<BlogSort> blogSortQueryWrapper = new QueryWrapper<>();
        blogSortQueryWrapper.ne(BlogSortSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        List<BlogSort> blogSortList = blogSortService.list(blogSortQueryWrapper);
        List<String> blogSortNameList = new ArrayList<>();
        List<Map> blogSortResultList = new ArrayList<>();
        for (BlogSort blogSort : blogSortList) {
            blogSortNameList.add(blogSort.getSortName());

            blogQueryWrapper = new QueryWrapper<>();
            blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
            blogQueryWrapper.eq(BlogSQLConstants.BLOG_SORT_UID, blogSort.getUid());
            Integer count = blogService.count(blogQueryWrapper);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("name", blogSort.getSortName());
            resultMap.put("value", count);
            blogSortResultList.add(resultMap);
        }
        Map<String, Object> blogSortMap = new HashMap<>();
        blogSortMap.put("blogSortNameList", blogSortNameList);
        blogSortMap.put("blogSortResultList", blogSortResultList);
        dataMap.put("blogSortList", blogSortMap);

        /*
         * 封装标签相关数据
         */
        QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.ne(TagSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        tagQueryWrapper.orderByDesc(TagSQLConstants.CLICK_COUNT);
        Page<Tag> tagPage = new Page<>();
        tagPage.setCurrent(1);
        tagPage.setSize(20);
        IPage<Tag> tagIPage = tagService.page(tagPage, tagQueryWrapper);
        dataMap.put("tagList", tagIPage.getRecords());


        return success(dataMap);
    }

}

