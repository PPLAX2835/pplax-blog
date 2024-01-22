package xyz.pplax.pplaxblog.admin.restapi;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.xo.global.BlogSQLConf;
import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.commons.base.enums.EStatus;
import xyz.pplax.pplaxblog.commons.base.global.response.ResponseResult;
import xyz.pplax.pplaxblog.utils.ResultUtil;
import xyz.pplax.pplaxblog.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 博客表 RestApi
 */
@RestController
@RequestMapping("${pplax.api.base-path}/blog")
@Api(value="博客RestApi", tags={"BlogRestApi"})
public class BlogRestApi {

	@Autowired
	BlogService blogService;

	@Autowired
	TagService tagService;

	@Autowired
	UserService userService;

	@Autowired
	BlogSortService blogSortService;

	private static final Logger log = LogManager.getLogger(BlogRestApi.class);

	@ApiOperation(value="获取博客列表", notes="获取博客列表", response = String.class)
	@GetMapping(value = "/getList")
	public String getList(HttpServletRequest request,
						  @ApiParam(name = "keyword", value = "关键字",required = false) @RequestParam(name = "keyword", required = false) String keyword,
						  @ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
						  @ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize) {

		QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
		if(!StringUtils.isEmpty(keyword)) {
			queryWrapper.like(BlogSQLConf.TITLE, keyword);
		}

		//分页
		Page<Blog> page = new Page<>();
		page.setCurrent(currentPage);
		page.setSize(pageSize);

		// 查询状态正常的
//		queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);			// 这个在前台就要取消注释
		// 按创建时间排序
//		queryWrapper.orderByDesc(BlogSQLConf.CREATE_TIME);

		// 查询
		IPage<Blog> pageList = blogService.page(page, queryWrapper);
		List<Blog> blogList = pageList.getRecords();

		for(Blog blog : blogList) {
			// 添加标签
			if(blog != null && !StringUtils.isEmpty(blog.getTagUid())) {
				String[] tagUids = blog.getTagUid().split(",");
				List<Tag> tagList = new ArrayList<>();
				for(String uid : tagUids) {
					Tag  tag = tagService.getById(uid);
					if(tag != null) {
						tagList.add(tag);
					}
				}
				blog.setTagList(tagList);
			}

			// 添加标题图片



			// 添加用户
			if(blog != null && !StringUtils.isEmpty(blog.getUserUid())) {
				User user = userService.getById(blog.getUserUid());
				if (user != null) {
					user.sensitiveDataRemove();
					blog.setUser(user);
				}
			}

			// 添加分类
			if(blog != null && !StringUtils.isEmpty(blog.getBlogSortUid())) {
				BlogSort blogSort = blogSortService.getById(blog.getBlogSortUid());
				if (blogSort != null) {
					blog.setBlogSort(blogSort);
				}
			}
		}

		log.info("返回结果");
		pageList.setRecords(blogList);

		return JSON.toJSONString(ResponseResult.success(pageList));
	}

	@ApiOperation(value="增加博客", notes="增加博客", response = String.class)
	@PostMapping("/add")
	public String add(HttpServletRequest request,
					  @ApiParam(name = "title", value = "博客标题",required = true) @RequestParam(name = "title", required = true) String title,
					  @ApiParam(name = "summary", value = "博客简介",required = false) @RequestParam(name = "summary", required = false) String summary,
					  @ApiParam(name = "content", value = "博客正文",required = false) @RequestParam(name = "content", required = false) String content,
					  @ApiParam(name = "tagUid", value = "标签uid",required = false) @RequestParam(name = "tagUid", required = false) String tagUid,
					  @ApiParam(name = "clickCount", value = "点击数",required = false) @RequestParam(name = "clickCount", required = false) Integer clickCount,
					  @ApiParam(name = "fileUid", value = "标题图Uid",required = false) @RequestParam(name = "fileUid", required = false) String fileUid) {

		if(StringUtils.isEmpty(title) || StringUtils.isEmpty(content)) {
			return ResultUtil.result(SysConf.ERROR, "必填项不能为空");
		}
		Blog blog = new Blog();
		blog.setTitle(title);
		blog.setSummary(summary);
//		blog.setContent(content);
		blog.setTagUid(tagUid);
		blog.setClickCount(clickCount);
		blog.setPictureUid(fileUid);
		blog.setStatus(EStatus.ENABLE);
		blogService.save(blog);
		return ResultUtil.result(SysConf.SUCCESS, "添加成功");
	}

	@ApiOperation(value="编辑博客", notes="编辑博客", response = String.class)
	@PostMapping("/edit")
	public String edit(HttpServletRequest request,
					   @ApiParam(name = "uid", value = "唯一UID",required = true) @RequestParam(name = "uid", required = true) String uid,
					   @ApiParam(name = "title", value = "博客标题",required = false) @RequestParam(name = "title", required = false) String title,
					   @ApiParam(name = "summary", value = "博客简介",required = false) @RequestParam(name = "summary", required = false) String summary,
					   @ApiParam(name = "content", value = "博客正文",required = false) @RequestParam(name = "content", required = false) String content,
					   @ApiParam(name = "tagUid", value = "标签UID",required = false) @RequestParam(name = "tagUid", required = false) String tagUid,
					   @ApiParam(name = "clickCount", value = "点击数",required = false) @RequestParam(name = "clickCount", required = false) Integer clickCount,
					   @ApiParam(name = "fileUid", value = "标题图UID",required = false) @RequestParam(name = "fileUid", required = false) String fileUid ) {

		if(StringUtils.isEmpty(uid)) {
			return ResultUtil.result(SysConf.ERROR, "数据错误");
		}
		Blog blog = blogService.getById(uid);
		blog.setTitle(title);
		blog.setSummary(summary);
//		blog.setContent(content);
		blog.setTagUid(tagUid);
		blog.setPictureUid(fileUid);
		blog.setClickCount(clickCount);
		blog.setStatus(EStatus.ENABLE);
		blog.updateById();
		return ResultUtil.result(SysConf.SUCCESS, "编辑成功");
	}

	@ApiOperation(value="删除博客", notes="删除博客", response = String.class)
	@PostMapping("/delete")
	public String delete(HttpServletRequest request,
						 @ApiParam(name = "uid", value = "唯一UID",required = true) @RequestParam(name = "uid", required = true) String uid			) {

		if(StringUtils.isEmpty(uid)) {
			return ResultUtil.result(SysConf.ERROR, "数据错误");
		}
		Blog blog = blogService.getById(uid);
		blog.setStatus(EStatus.DISABLED);
		blog.updateById();
		return ResultUtil.result(SysConf.SUCCESS, "删除成功");
	}


}

