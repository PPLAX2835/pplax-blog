package xyz.pplax.pplaxblog.admin.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.BlogGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.service.BlogService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

	@ApiOperation(value="获取博客列表", notes="获取博客列表")
	@GetMapping("/list")
	public String getList(
			@RequestParam(value = "blogTitle", required = false) String blogTitle,
			@RequestParam(value = "blogSortUid", required = false) String blogSortUid,
			@RequestParam(value = "tagUid", required = false) String tagUid,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "currentPage") Long currentPage,
			@RequestParam(value = "pageSize") Long pageSize
	) {

		Page<Blog> blogPage = blogService.page(blogTitle, blogSortUid, tagUid, status, currentPage, pageSize);

		return toJson(ResponseResult.success(blogPage.getRecords(), blogPage.getTotal()));
	}


	@ApiOperation(value="获取博客内容", notes="获取博客内容")
	@GetMapping("/{blogUid}/content")
	public String getBlogContent(@PathVariable("blogUid") String blogUid) {

		return success(blogService.getBlogContentByBlogUid(blogUid));
	}


	@ApiOperation(value="编辑博客", notes="编辑博客")
	@PutMapping("/{blogUid}")
	public String updateBlog(@PathVariable("blogUid") String blogUid, @RequestBody @Validated(value = {Update.class}) BlogEditDto blogEditDto) {
		blogEditDto.setUid(blogUid);
		Boolean res = blogService.updateById(blogEditDto);

		if (res) {
			return success();
		}
		return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
	}


	@ApiOperation(value="添加博客", notes="添加博客")
	@PostMapping(value = "")
	public String add(HttpServletRequest httpServletRequest, @RequestBody @Validated(value = {Insert.class}) BlogEditDto blogEditDto) {
		String authorization = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
		String payloadByBase64 = JwtUtil.getPayloadByBase64(authorization);
		String userUid = JSON.parseObject(payloadByBase64).get("uid").toString();
		blogEditDto.setUserUid(userUid);

		Blog blog = blogService.save(blogEditDto);

		if (blog != null) {
			return success(blog);
		}
		return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
	}


	@ApiOperation(value="删除博客", notes="删除博客")
	@DeleteMapping("/{blogUid}")
	public String delete(@PathVariable("blogUid") String blogUid) {
		Boolean res = blogService.removeById(blogUid);
		if (res != null) {
			return success();
		}
		return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	@ApiOperation(value = "批量删除博客", notes = "批量删除博客")
	@DeleteMapping(value = "")
	public String delete(@RequestBody List<String> blogUidList) {
		Boolean res = blogService.removeByIds(blogUidList);
		if (res != null) {
			return success();
		}
		return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
	}


	@ApiOperation(value="置顶博客", notes="置顶博客")
	@PutMapping("/{blogUid}/promote")
	public String promote(@PathVariable("blogUid") String blogUid) {
		boolean res = blogService.promote(blogUid);

		if (res) {
			return success();
		}
		return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
	}


	@ApiOperation(value="取消置顶", notes="取消置顶")
	@DeleteMapping("/{blogUid}/promote")
	public String promoteCancel(@PathVariable("blogUid") String blogUid) {
		boolean res = blogService.promoteCancel(blogUid);

		if (res) {
			return success();
		}
		return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
	}
}

