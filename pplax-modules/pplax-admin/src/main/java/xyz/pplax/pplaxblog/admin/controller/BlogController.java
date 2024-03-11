package xyz.pplax.pplaxblog.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.list.BlogGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;

/**
 * 博客表 Controller
 */
@RestController
@RequestMapping("/blog")
@Api(value="博客Controller", tags={"博客Controller"})
public class BlogController extends SuperController {

	@Autowired
    private BlogService blogService;

	private static final Logger log = LogManager.getLogger(BlogController.class);

	@ApiOperation(value="获取分类列表", notes="获取分类列表")
	@GetMapping("/list")
	public String getList(
			@RequestParam(value = "blogTitle", required = false) String blogTitle,
			@RequestParam(value = "blogSortUid", required = false) String blogSortUid,
			@RequestParam(value = "tagUid", required = false) String tagUid,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "currentPage") Long currentPage,
			@RequestParam(value = "pageSize") Long pageSize
	) {
		// 封装
		BlogGetListDto blogGetListDto = new BlogGetListDto();
		blogGetListDto.setBlogTitle(blogTitle);
		blogGetListDto.setBlogSortUid(blogSortUid);
		blogGetListDto.setTagUid(tagUid);
		blogGetListDto.setStatus(status);
		blogGetListDto.setCurrentPage(currentPage);
		blogGetListDto.setPageSize(pageSize);

		IPage<Blog> blogIPage = blogService.list(blogGetListDto);

		return toJson(ResponseResult.success(blogIPage.getRecords(), blogIPage.getTotal()));
	}


}

