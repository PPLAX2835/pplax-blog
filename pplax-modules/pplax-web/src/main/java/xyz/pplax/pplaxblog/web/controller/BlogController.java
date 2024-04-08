package xyz.pplax.pplaxblog.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
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
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;
import xyz.pplax.pplaxblog.xo.service.site.SiteService;

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
    private BlogService blogService;

    @ApiOperation(value = "获取博客列表", httpMethod = "GET", response = ResponseResult.class, notes = "网站相关信息")
    @GetMapping("/list")
    public String getBlogList(
            @RequestParam(value = "blogSortUid", required = false) String blogSortUid,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){
        return success(blogService.listByBlogSort(blogSortUid, currentPage, pageSize).getRecords());
    }



}

