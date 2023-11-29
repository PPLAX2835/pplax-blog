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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.pplaxblog.admin.global.BlogSQLConf;
import xyz.pplax.pplaxblog.admin.global.BlogSortSQLConf;
import xyz.pplax.pplaxblog.base.response.ResponseResult;
import xyz.pplax.pplaxblog.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.service.BlogSortService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/blogSort")
@Api(value="博客分类RestApi", tags={"BlogSortRestApi"})
public class BlogSortRestApi {

    @Autowired
    private BlogSortService blogSortService;

    private static final Logger log = LogManager.getLogger(BlogSortRestApi.class);

    @ApiOperation(value="获取博客分类列表", notes="获取博客分类列表", response = String.class)
    @GetMapping(value = "/getList")
    public String getList(HttpServletRequest request,
                          @ApiParam(name = "keyword", value = "关键字",required = false) @RequestParam(name = "keyword", required = false) String keyword,
                          @ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
                          @ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize) {

        QueryWrapper<BlogSort> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(keyword)) {
            queryWrapper.like(BlogSortSQLConf.SORT_NAME, keyword);
        }

        //分页
        Page<BlogSort> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        // 按创建时间排序
        queryWrapper.orderByDesc(BlogSQLConf.CREATE_TIME);

        // 查询
        IPage<BlogSort> pageList = blogSortService.page(page, queryWrapper);
        List<BlogSort> blogSortListList = pageList.getRecords();

        for(BlogSort blogSort : blogSortListList) {
            // 添加父级分类
            if(blogSort != null && !StringUtils.isEmpty(blogSort.getParentUid())) {
                String parentUid = blogSort.getParentUid();
                BlogSort parentBlogSort = blogSortService.getById(parentUid);
                if(parentBlogSort != null) {
                    blogSort.setParentBlogSort(parentBlogSort);
                }
            }
        }

        log.info("返回结果");
        pageList.setRecords(blogSortListList);

        return JSON.toJSONString(ResponseResult.success(pageList));
    }

}
