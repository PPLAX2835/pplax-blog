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
import xyz.pplax.pplaxblog.commons.validator.group.*;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.TagEditDto;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.service.TagService;

import java.util.List;

/**
 * 标签表 Controller
 */
@RestController
@RequestMapping("/tag")
@Api(value="标签Controller", tags={"标签Controller"})
public class TagController extends SuperController {
    @Autowired
    private TagService tagService;

    private static Logger log = LogManager.getLogger(TagController.class);

    @ApiOperation(value="获取标签列表", notes="获取标签列表")
    @GetMapping("/list")
    public String getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortByClickCount", required = false) Boolean sortByClickCount,
            @RequestParam(value = "sortByCites", required = false) Boolean sortByCites,
            @RequestParam(value = "currentPage", required = false) Long currentPage,
            @RequestParam(value = "pageSize", required = false) Long pageSize
    ) {

        Page<Tag> tagIPage = tagService.page(keyword, sortByClickCount, sortByCites, currentPage, pageSize);

        return toJson(ResponseResult.success(tagIPage.getRecords(), tagIPage.getTotal()));
    }

    @ApiOperation(value="编辑标签", notes="编辑标签")
    @PutMapping("/{tagUid}")
    public String update(@PathVariable("tagUid") String tagUid, @RequestBody @Validated(value = {Update.class}) TagEditDto tagEditDto) {
        if (tagService.isTagNameExist(tagEditDto.getName())) {
            Tag tag = tagService.getById(tagUid);
            if (!tag.getName().equals(tagEditDto.getName())) {
                return toJson(ResponseResult.error(HttpStatus.TAG_NAME_EXIST));
            }
        }

        tagEditDto.setUid(tagUid);
        Boolean res = tagService.updateById(tagEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value="新增标签", notes="新增标签")
    @PostMapping("")
    public String add(@RequestBody @Validated(value = {Insert.class}) TagEditDto tagEditDto) {
        if (tagService.isTagNameExist(tagEditDto.getName())) {
            return toJson(ResponseResult.error(HttpStatus.TAG_NAME_EXIST));
        }

        Boolean res = tagService.save(tagEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @ApiOperation(value="删除标签", notes="删除标签")
    @DeleteMapping("/{tagUid}")
    public String delete(@PathVariable("tagUid") String tagUid) {
        Boolean res = tagService.removeById(tagUid);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value = "批量删除标签", notes = "批量删除标签")
    @DeleteMapping(value = "")
    public String delete(@RequestBody List<String> tagUidList) {
        Boolean res = tagService.removeByIds(tagUidList);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value="判断标签名是否存在", notes="判断标签名是否存在")
    @GetMapping(value = "/exist")
    public String isUsernameExist(@RequestParam("tagName") String tagName) {
        return success(tagService.isTagNameExist(tagName));
    }
}

