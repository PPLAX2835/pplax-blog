package xyz.pplax.pplaxblog.admin.restapi;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.admin.global.BlogSQLConf;
import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.base.dto.BaseDto;
import xyz.pplax.pplaxblog.base.enums.EStatus;
import xyz.pplax.pplaxblog.base.response.ResponseResult;
import xyz.pplax.pplaxblog.utils.ResultUtil;
import xyz.pplax.pplaxblog.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.service.BlogService;
import xyz.pplax.pplaxblog.xo.service.TagService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试 RestApi
 */
@RestController
@RequestMapping("/test")
@Api(value="测试RestApi", tags={"TestRestApi"})
public class TestRestApi {

	@Autowired
	BlogService blogService;

	@Autowired
	TagService tagService;

	private static final Logger log = LogManager.getLogger(TestRestApi.class);

	@ApiOperation(value="获取博客列表", notes="获取博客列表", response = String.class)
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public String getList(HttpServletRequest request, @RequestBody BaseDto baseDto) {

		String keyword = baseDto.getKeyWord();
		Long currentPage = baseDto.getCurrentPage();
		Long pageSize = baseDto.getPageSize();


		QueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>();
		if(!StringUtils.isEmpty(keyword)) {
			queryWrapper.like(BlogSQLConf.TITLE, keyword);
		}

		//分页
		Page<Blog> page = new Page<>();
		page.setCurrent(currentPage);
		page.setSize(pageSize);

		queryWrapper.eq(BlogSQLConf.STATUS, EStatus.ENABLE);

		queryWrapper.orderByDesc(BlogSQLConf.CREATE_TIME);

		IPage<Blog> pageList = blogService.page(page, queryWrapper);
		List<Blog> list = pageList.getRecords();
		for(Blog item : list) {
			if(item != null && !StringUtils.isEmpty(item.getTagUid())) {
				String uids[] = item.getTagUid().split(",");
				List<Tag> tagList = new ArrayList<Tag>();
				for(String uid : uids) {
					Tag  tag = tagService.getById(uid);
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


	@ApiOperation(value="添加博客分类", notes="添加博客分类", response = String.class)
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String edit(HttpServletRequest request
	) {

		return JSON.toJSONString(ResponseResult.success());
	}

}

