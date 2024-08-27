package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.validator.group.*;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogSortEditDto;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;
import xyz.pplax.pplaxblog.xo.service.BlogSortService;

import java.util.List;

@RestController
@RequestMapping("/blogSort")
@Api(value="博客分类Controller", tags={"博客分类Controller"})
public class BlogSortController extends SuperController {

    @Autowired
    private BlogSortService blogSortService;

    private static final Logger log = LogManager.getLogger(BlogSortController.class);

    @ApiOperation(value="获取分类列表", notes="获取分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword",value = "关键词", defaultValue = "",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "sortByClickCount",value = "按点击量排序",defaultValue = "false",paramType = "query",dataType="Boolean",required = false),
            @ApiImplicitParam(name = "sortByCites",value = "按引用量排序",defaultValue = "false",paramType = "query",dataType="Boolean",required = false),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/list")
    public ResponseResult getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortByClickCount", required = false) Boolean sortByClickCount,
            @RequestParam(value = "sortByCites", required = false) Boolean sortByCites,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ) {
        // 封装
        Page<BlogSort> blogSortPage = blogSortService.page(keyword, sortByClickCount, sortByCites, currentPage, pageSize);

        return ResponseResult.success(blogSortPage.getRecords(), blogSortPage.getTotal());
    }

    @ApiOperation(value="编辑分类", notes="编辑分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogSortUid",value = "博客分类uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @PutMapping("/{blogSortUid}")
    public ResponseResult update(@PathVariable("blogSortUid") String blogSortUid, @RequestBody @Validated(value = {Update.class}) BlogSortEditDto blogSortEditDto) {
        blogSortEditDto.setUid(blogSortUid);
        blogSortService.updateById(blogSortEditDto);

        return success();
    }

    @ApiOperation(value="新增分类", notes="新增分类")
    @PostMapping("")
    public ResponseResult add(@RequestBody @Validated(value = {Insert.class}) BlogSortEditDto blogSortEditDto) {
        blogSortService.save(blogSortEditDto);

        return success();
    }

    @ApiOperation(value="删除分类", notes="删除分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogSortUid",value = "博客分类uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{blogSortUid}")
    public ResponseResult delete(@PathVariable("blogSortUid") String blogSortUid) {
        blogSortService.removeById(blogSortUid);
        return success();
    }

    @ApiOperation(value = "批量删除分类", notes = "批量删除分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogSortUidList",value = "博客分类uidList", defaultValue = "",dataType="List<String>",required = true)
    })
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> blogSortUidList) {

        blogSortService.removeByIds(blogSortUidList);
        return success();
    }

    @ApiOperation(value="置顶分类", notes="置顶分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogSortUid",value = "博客分类uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @PutMapping("/{blogSortUid}/promote")
    public ResponseResult promote(@PathVariable("blogSortUid") String blogSortUid) {
        blogSortService.promote(blogSortUid);

        return success();
    }


    @ApiOperation(value="取消置顶", notes="取消置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogSortUid",value = "博客分类uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{blogSortUid}/promote")
    public ResponseResult promoteCancel(@PathVariable("blogSortUid") String blogSortUid) {
        blogSortService.promoteCancel(blogSortUid);

        return success();
    }


}
