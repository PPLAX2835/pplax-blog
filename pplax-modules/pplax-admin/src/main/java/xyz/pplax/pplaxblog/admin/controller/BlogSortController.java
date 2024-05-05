package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.validator.group.*;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogSortEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.BlogSortGetListDto;
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
    @GetMapping("/list")
    public String getList(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "sortByClickCount", required = false) Boolean sortByClickCount,
        @RequestParam(value = "sortByCites", required = false) Boolean sortByCites,
        @RequestParam(value = "currentPage", required = false) Long currentPage,
        @RequestParam(value = "pageSize", required = false) Long pageSize
    ) {
        // 封装
        Page<BlogSort> blogSortPage = blogSortService.page(keyword, sortByClickCount, sortByCites, currentPage, pageSize);

        return toJson(ResponseResult.success(blogSortPage.getRecords(), blogSortPage.getTotal()));
    }

    @ApiOperation(value="编辑分类", notes="编辑分类")
    @PutMapping("/{blogSortUid}")
    public String update(@PathVariable("blogSortUid") String blogSortUid, @RequestBody @Validated(value = {Update.class}) BlogSortEditDto blogSortEditDto) {
        blogSortEditDto.setUid(blogSortUid);
        Boolean res = blogSortService.updateById(blogSortEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value="新增分类", notes="新增分类")
    @PostMapping("")
    public String add(@RequestBody @Validated(value = {Insert.class}) BlogSortEditDto blogSortEditDto) {
        Boolean res = blogSortService.save(blogSortEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value="删除分类", notes="删除分类")
    @DeleteMapping("/{blogSortUid}")
    public String delete(@PathVariable("blogSortUid") String blogSortUid) {
        return toJson(blogSortService.removeById(blogSortUid));
    }

    @ApiOperation(value = "批量删除分类", notes = "批量删除分类")
    @DeleteMapping(value = "")
    public String delete(@RequestBody List<String> blogSortUidList) {
        return toJson(blogSortService.removeByIds(blogSortUidList));
    }

    @ApiOperation(value="置顶分类", notes="置顶分类")
    @PutMapping("/{blogSortUid}/promote")
    public String promote(@PathVariable("blogSortUid") String blogSortUid) {
        Boolean res = blogSortService.promote(blogSortUid);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @ApiOperation(value="取消置顶", notes="取消置顶")
    @DeleteMapping("/{blogSortUid}/promote")
    public String promoteCancel(@PathVariable("blogSortUid") String blogSortUid) {
        Boolean res = blogSortService.promoteCancel(blogSortUid);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }


}
