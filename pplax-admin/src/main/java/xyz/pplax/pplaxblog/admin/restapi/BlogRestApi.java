package xyz.pplax.pplaxblog.admin.restapi;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.admin.global.SQLConf;
import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.base.enums.EStatus;
import xyz.pplax.pplaxblog.utils.ResultUtil;
import xyz.pplax.pplaxblog.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.pplax.pplaxblog.xo.service.TagService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 博客表 RestApi
 * </p>
 */
@RestController
@RequestMapping("/blog")
@Api(value="博客RestApi", tags={"BlogRestApi"})
public class BlogRestApi {

	@Autowired
	BlogService blogService;

	@Autowired
	TagService tagService;

	private static Logger log = LogManager.getLogger(AdminRestApi.class);

	@ApiOperation(value="获取博客列表", notes="获取博客列表", response = String.class)
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public String getList(HttpServletRequest request,
						  @ApiParam(name = "keyword", value = "关键字",required = false) @RequestParam(name = "keyword", required = false) String keyword,
						  @ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
						  @ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize) {

		QueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>();
		if(!StringUtils.isEmpty(keyword)) {
			queryWrapper.like(SQLConf.TITLE, keyword);
		}

		//分页
		Page<Blog> page = new Page<>();
		page.setCurrent(currentPage);
		page.setSize(pageSize);

		queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);

		queryWrapper.orderByDesc(SQLConf.CREATE_TIME);

		IPage<Blog> pageList = blogService.page(page, queryWrapper);
		List<Blog> list = pageList.getRecords();
		for(Blog item : list) {
			if(item != null && !StringUtils.isEmpty(item.getTagUid())) {
				String uids[] = item.getTagUid().split(",");
				List<Tag> tagList = new ArrayList<Tag>();
				for(String uid : uids) {
					Tag tag = tagService.getById(uid);
					if(tag != null && tag.getStatus() != EStatus.DISABLED) {
						tagList.add(tag);
					}
				}
				item.setTagList(tagList);
			}
		}
		log.info("返回结果");
		pageList.setRecords(list);
		return ResultUtil.result(SysConf.SUCCESS, pageList);
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
		blog.setContent(content);
		blog.setTagUid(tagUid);
		blog.setClickCount(clickCount);
		blog.setFileUid(fileUid);
		blog.setStatus(EStatus.ENABLE);
		blog.setCreateTime(new Date());
		blog.setUpdateTime(new Date());
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
		blog.setContent(content);
		blog.setTagUid(tagUid);
		blog.setFileUid(fileUid);
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

