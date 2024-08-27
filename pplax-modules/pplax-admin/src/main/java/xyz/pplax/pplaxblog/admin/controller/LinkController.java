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
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.LinkEditDto;
import xyz.pplax.pplaxblog.xo.entity.Link;
import xyz.pplax.pplaxblog.xo.service.LinkService;

import java.util.List;

@RestController
@RequestMapping("/link")
@Api(value="友情链接Controller", tags={"友情链接Controller"})
public class LinkController extends SuperController {

    @Autowired
    private LinkService linkService;

    private static final Logger log = LogManager.getLogger(LinkController.class);

    @ApiOperation(value="获取友链列表", notes="获取友链列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword",value = "关键词", defaultValue = "",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "status",value = "状态",defaultValue = "",paramType = "query",dataType="Integer",required = false),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/list")
    public ResponseResult getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ) {

        Page<Link> linkIPage = linkService.page(keyword, status, currentPage, pageSize);

        return ResponseResult.success(linkIPage.getRecords(), linkIPage.getTotal());
    }

    @ApiOperation(value="编辑友链", notes="编辑友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "linkUid",value = "友链uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @PutMapping("/{linkUid}")
    public ResponseResult update(@PathVariable("linkUid") String linkUid, @RequestBody @Validated(value = {Update.class}) LinkEditDto linkEditDto) {

        linkEditDto.setUid(linkUid);

        linkService.updateById(linkEditDto);

        return success();
    }


    @ApiOperation(value="新增友链", notes="新增友链")
    @PostMapping("")
    public ResponseResult add(@RequestBody @Validated(value = {Insert.class}) LinkEditDto linkEditDto) {
        linkService.save(linkEditDto);

        return success();
    }


    @ApiOperation(value="删除友链", notes="删除友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "linkUid",value = "友链uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{linkUid}")
    public ResponseResult delete(@PathVariable("linkUid") String linkUid) {
        linkService.removeById(linkUid);

        return success();
    }

    @ApiOperation(value = "批量删除友链", notes = "批量删除友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "linkUidList",value = "友链uidList", defaultValue = "",dataType="List<String>",required = true)
    })
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> linkUidList) {
        linkService.removeByIds(linkUidList);

        return success();
    }
}
