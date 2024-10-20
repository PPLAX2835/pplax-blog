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

        Page<Tag> tagIPage = tagService.page(keyword, sortByClickCount, sortByCites, currentPage, pageSize);

        return ResponseResult.success(tagIPage.getRecords(), tagIPage.getTotal());
    }

    @ApiOperation(value="编辑标签", notes="编辑标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagUid",value = "标签uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @PutMapping("/{tagUid}")
    public ResponseResult update(@PathVariable("tagUid") String tagUid, @RequestBody @Validated(value = {Update.class}) TagEditDto tagEditDto) {
        if (tagService.isTagNameExist(tagEditDto.getName())) {
            Tag tag = tagService.getById(tagUid);
            if (!tag.getName().equals(tagEditDto.getName())) {
                return ResponseResult.error(HttpStatus.TAG_NAME_EXIST);
            }
        }

        tagEditDto.setUid(tagUid);
        Boolean res = tagService.updateById(tagEditDto);

        return success();
    }

    @ApiOperation(value="新增标签", notes="新增标签")
    @PostMapping("")
    public ResponseResult add(@RequestBody @Validated(value = {Insert.class}) TagEditDto tagEditDto) {
        if (tagService.isTagNameExist(tagEditDto.getName())) {
            return ResponseResult.error(HttpStatus.TAG_NAME_EXIST);
        }

        Boolean res = tagService.save(tagEditDto);

        return success();
    }


    @ApiOperation(value="删除标签", notes="删除标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagUid",value = "标签uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{tagUid}")
    public ResponseResult delete(@PathVariable("tagUid") String tagUid) {
        Boolean res = tagService.removeById(tagUid);

        return success();
    }

    @ApiOperation(value = "批量删除标签", notes = "批量删除标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagUidList",value = "标签uidList", defaultValue = "",dataType="List<String>",required = true)
    })
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> tagUidList) {
        Boolean res = tagService.removeByIds(tagUidList);

        return success();
    }

    @ApiOperation(value="判断标签名是否存在", notes="判断标签名是否存在")
    @GetMapping(value = "/exist")
    public ResponseResult isUsernameExist(@RequestParam("tagName") String tagName) {
        return success(tagService.isTagNameExist(tagName));
    }
}

