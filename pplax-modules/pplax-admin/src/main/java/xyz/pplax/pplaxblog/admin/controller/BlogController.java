package xyz.pplax.pplaxblog.admin.controller;


import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;
import xyz.pplax.pplaxblog.xo.service.blogsort.BlogSortService;
import xyz.pplax.pplaxblog.xo.service.tag.TagService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;

/**
 * 博客表 Controller
 */
@RestController
@RequestMapping("/blog")
@Api(value="博客Controller", tags={"博客Controller"})
public class BlogController {

	@Autowired
    BlogService blogService;

	@Autowired
    TagService tagService;

	@Autowired
	UserService userService;

	@Autowired
	BlogSortService blogSortService;

	private static final Logger log = LogManager.getLogger(BlogController.class);



}

