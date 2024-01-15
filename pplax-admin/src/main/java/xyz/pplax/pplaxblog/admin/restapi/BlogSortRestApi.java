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
import xyz.pplax.pplaxblog.admin.dto.add.BlogSortAddDto;
import xyz.pplax.pplaxblog.admin.dto.edit.BlogSortEditDto;
import xyz.pplax.pplaxblog.base.global.BaseMessageConf;
import xyz.pplax.pplaxblog.xo.dto.BlogSortDto;
import xyz.pplax.pplaxblog.xo.global.BlogSortSQLConf;
import xyz.pplax.pplaxblog.base.global.response.ResponseCode;
import xyz.pplax.pplaxblog.base.global.response.ResponseResult;
import xyz.pplax.pplaxblog.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.service.BlogSortService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/blogSort")
@Api(value="博客分类RestApi", tags={"BlogSortRestApi"})
public class BlogSortRestApi {

    @Autowired
    private BlogSortService blogSortService;

    private static final Logger log = LogManager.getLogger(BlogSortRestApi.class);

    /**
     * 获取博客分类列表，get方法
     * @param request
     * @param keyword
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ApiOperation(value="获取博客分类列表", notes="获取博客分类列表", response = String.class)
    @GetMapping(value = "")
    public String getList(HttpServletRequest request,
                          @ApiParam(name = "keyword", value = "关键字",required = false) @RequestParam(name = "keyword", required = false) String keyword,
                          @ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
                          @ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                          @ApiParam(name = "status", value = "状态",required = false) @RequestParam(name = "status", required = false, defaultValue = "10") Integer status,
                          @ApiParam(name = "sortByClickCount", value = "是否按点击量排序", required = false) @RequestParam(name = "sortByClickCount", required = false) boolean sortByClickCount
    ) {
        // 封装
        BlogSortDto blogSortDto = new BlogSortDto();
        blogSortDto.setKeyword(keyword);
        blogSortDto.setCurrentPage(currentPage);
        blogSortDto.setPageSize(pageSize);
        blogSortDto.setStatus(status);
        blogSortDto.setSortByClickCount(sortByClickCount);

        log.info("返回结果");

        return JSON.toJSONString(ResponseResult.success(blogSortService.getPageList(blogSortDto)));
    }

    /**
     * 检查是否重名，get方法
     * @param request
     * @param sortName
     * @return
     */
    @ApiOperation(value="检查是否重名", notes="检查是否重名", response = String.class)
    @GetMapping(value = "/{sortName}/exists")
    public String checkSortNameExists(
            HttpServletRequest request,
            @ApiParam(name = "sortName", value = "分类名",required = true) @PathVariable String sortName
    ) {

        // 封装
        BlogSortDto blogSortDto = new BlogSortDto();
        blogSortDto.setSortName(sortName);

        return JSON.toJSONString(ResponseResult.success(blogSortService.checkSortNameExists(blogSortDto)));
    }

    /**
     *  新增博客分类，post方法
     * @param request
     * @param blogSortDto
     * @return
     */
    @ApiOperation(value="增加博客分类", notes="增加博客分类", response = String.class)
    @PostMapping("")
    public String add(
            HttpServletRequest request,
            @RequestBody BlogSortDto blogSortDto
    ) {

        log.info("返回结果");
        return blogSortService.addBlogSort(blogSortDto);
    }


    /**
     * 编辑博客分类，put方法
     * @param request
     * @param blogSortDto
     * @return
     */
    @ApiOperation(value="编辑博客分类", notes="编辑博客分类", response = String.class)
    @PutMapping("/{uid}")
    public String edit(
            HttpServletRequest request,
            @ApiParam(name = "uid", value = "唯一标识符",required = true) @PathVariable String uid,
            @RequestBody BlogSortDto blogSortDto
    ) {
        blogSortDto.setUid(uid);

        log.info("返回结果");
        return  blogSortService.editBlogSort(blogSortDto);
    }

    /**
     * 删除博客分类 delete方法
     * @param request
     * @param uid
     * @return
     */
    @ApiOperation(value="删除博客分类", notes="删除博客分类", response = String.class)
    @DeleteMapping("/{uid}")
    public String physicalDelete(
            HttpServletRequest request,
            @ApiParam(name = "uid", value = "唯一标识符",required = true) @PathVariable String uid
    ) {

        log.info("返回结果");
        return blogSortService.logicDelete(uid);
    }

    /**
     * 批量删除博客分类 delete方法
     * @param request
     * @param blogSortDto
     * @return
     */
    @ApiOperation(value="批量删除博客分类", notes="批量删除博客分类", response = String.class)
    @DeleteMapping("/batch")
    public String physicalBatchDelete(
            HttpServletRequest request,
            @RequestBody BlogSortDto blogSortDto
    ) {

        log.info("返回结果");
        return blogSortService.logicBatchDelete(blogSortDto);
    }



}
