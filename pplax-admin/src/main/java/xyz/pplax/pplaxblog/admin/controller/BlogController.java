package xyz.pplax.pplaxblog.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 博客表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/blog")
public class BlogController {
	
	@Autowired
	BlogService blogService;
	
	@GetMapping("/getList")
	public String getList() {
		List<Blog> list =  blogService.list(new QueryWrapper<Blog>().eq(SysConf.status, "1"));
		String str = list.toString(); 
		return str;
	}
}

