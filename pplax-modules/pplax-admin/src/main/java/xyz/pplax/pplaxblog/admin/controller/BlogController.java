package xyz.pplax.pplaxblog.admin.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	@ApiImplicitParams({
			@ApiImplicitParam(name = "blogTitle",value = "博客名", defaultValue = "",paramType = "query",dataType="String",required = false),
			@ApiImplicitParam(name = "blogSortUid",value = "博客分类uid",defaultValue = "",paramType = "query",dataType="String",required = false),
			@ApiImplicitParam(name = "tagUid",value = "标签uid",defaultValue = "",paramType = "query",dataType="String",required = false),
			@ApiImplicitParam(name = "status",value = "状态",defaultValue = "",paramType = "query",dataType="Integer",required = false),
			@ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
			@ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
	})
	public ResponseResult getList(
			@RequestParam(value = "blogTitle", required = false) String blogTitle,
			@RequestParam(value = "blogSortUid", required = false) String blogSortUid,
			@RequestParam(value = "tagUid", required = false) String tagUid,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
	) {

		Page<Blog> blogPage = blogService.page(blogTitle, blogSortUid, tagUid, status, currentPage, pageSize);

		return ResponseResult.success(blogPage.getRecords(), blogPage.getTotal());
	}


	@ApiOperation(value="获取博客内容", notes="获取博客内容")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "blogUid",value = "博客uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
	})
	@GetMapping("/{blogUid}/content")
	public ResponseResult getBlogContent(@PathVariable("blogUid") String blogUid) {

		return success(blogService.getBlogContentByBlogUid(blogUid));
	}


	@ApiOperation(value="编辑博客", notes="编辑博客")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "blogUid",value = "博客uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
	})
	@PutMapping("/{blogUid}")
	public ResponseResult updateBlog(@PathVariable("blogUid") String blogUid, @RequestBody @Validated(value = {Update.class}) BlogEditDto blogEditDto) {
		blogEditDto.setUid(blogUid);
		blogService.updateById(blogEditDto);

		return success();
	}


	@ApiOperation(value="添加博客", notes="添加博客")
	@PostMapping(value = "")
	public ResponseResult add(HttpServletRequest httpServletRequest, @RequestBody @Validated(value = {Insert.class}) BlogEditDto blogEditDto) {
		String authorization = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
		String payloadByBase64 = JwtUtil.getPayloadByBase64(authorization);
		String userUid = JSON.parseObject(payloadByBase64).get("uid").toString();
		blogEditDto.setUserUid(userUid);

		Blog blog = blogService.save(blogEditDto);

		if (blog != null) {
			return success(blog);
		}
		return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ApiOperation(value="删除博客", notes="删除博客")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "blogUid",value = "博客uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
	})
	@DeleteMapping("/{blogUid}")
	public ResponseResult delete(@PathVariable("blogUid") String blogUid) {
		Boolean res = blogService.removeById(blogUid);
		if (res != null) {
			return success();
		}
		return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(value = "批量删除博客", notes = "批量删除博客")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "blogUidList",value = "博客uids", defaultValue = "",dataType="List<String>",required = true)
	})
	@DeleteMapping(value = "")
	public ResponseResult delete(@RequestBody List<String> blogUidList) {
		Boolean res = blogService.removeByIds(blogUidList);
		if (res != null) {
			return success();
		}
		return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(value="置顶博客", notes="置顶博客")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "blogUid",value = "博客uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
	})
	@PutMapping("/{blogUid}/promote")
	public ResponseResult promote(@PathVariable("blogUid") String blogUid) {
		blogService.promote(blogUid);

		return success();
	}

	@ApiOperation(value="取消置顶", notes="取消置顶")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "blogUid",value = "博客uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
	})
	@DeleteMapping("/{blogUid}/promote")
	public ResponseResult promoteCancel(@PathVariable("blogUid") String blogUid) {
		blogService.promoteCancel(blogUid);

		return success();
	}
}

