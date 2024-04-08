package xyz.pplax.pplaxblog.web.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;
import xyz.pplax.pplaxblog.xo.service.blogsort.BlogSortService;

import java.util.List;


/**
 * 博客分类 Controller
 */
@RestController
@RequestMapping("/blogSort")
@Api(value="博客分类Controller", tags={"博客分类Controller"})
public class BlogSortController extends SuperController {

    private static Logger log = LogManager.getLogger(BlogSortController.class);

    @Autowired
    private BlogSortService blogSortService;

    @ApiOperation(value = "获取博客列表", httpMethod = "GET", response = ResponseResult.class, notes = "网站相关信息")
    @GetMapping("/list")
    public String getBlogSortList(){
        return success(blogSortService.list());
    }
}

