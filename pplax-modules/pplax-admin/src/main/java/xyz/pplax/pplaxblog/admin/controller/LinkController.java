package xyz.pplax.pplaxblog.admin.controller;

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
    @GetMapping("/list")
    public String getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "currentPage", required = false) Long currentPage,
            @RequestParam(value = "pageSize", required = false) Long pageSize
    ) {

        Page<Link> linkIPage = linkService.page(keyword, status, currentPage, pageSize);

        return toJson(ResponseResult.success(linkIPage.getRecords(), linkIPage.getTotal()));
    }

    @ApiOperation(value="编辑友链", notes="编辑友链")
    @PutMapping("/{linkUid}")
    public String update(@PathVariable("linkUid") String linkUid, @RequestBody @Validated(value = {Update.class}) LinkEditDto linkEditDto) {

        linkEditDto.setUid(linkUid);
        Boolean res = linkService.updateById(linkEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @ApiOperation(value="新增友链", notes="新增友链")
    @PostMapping("")
    public String add(@RequestBody @Validated(value = {Insert.class}) LinkEditDto linkEditDto) {
        Boolean res = linkService.save(linkEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @ApiOperation(value="删除标签", notes="删除标签")
    @DeleteMapping("/{linkUid}")
    public String delete(@PathVariable("linkUid") String linkUid) {
        boolean res = linkService.removeById(linkUid);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value = "批量删除标签", notes = "批量删除标签")
    @DeleteMapping(value = "")
    public String delete(@RequestBody List<String> linkUidList) {
        boolean res = linkService.removeByIds(linkUidList);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
