package xyz.pplax.pplaxblog.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 博客表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/blog")
@Api(value="博客RestApi",tags={"博客操作接口"})
public class BlogController {

	@Autowired
	BlogService blogService;

	private static Logger log = LogManager.getLogger(AdminController.class);

	@ApiOperation(value="获取博客列表", notes="获取博客列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", value = "当前页数", required = false, dataType = "Integer"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示数目", required = false, dataType = "Integer")
	})
	@GetMapping("/getList")
	public String getList(HttpServletRequest request,
						  @RequestParam(name = "currentPage", required = false, defaultValue = "1") Integer currentPage,
						  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

		QueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>();
		List<Blog> list = blogService.list(queryWrapper);
		log.info(list.toString());
		return list.toString();
	}
}


