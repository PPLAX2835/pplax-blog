package xyz.pplax.pplaxblog.web.controller;

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
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.TagEditDto;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.service.TagService;


/**
 * 标签 Controller
 */
@RestController
@RequestMapping("/tag")
@Api(value="标签Controller", tags={"标签Controller"})
public class TagController extends SuperController {

    private static Logger log = LogManager.getLogger(TagController.class);

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "获取标签列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取标签列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword",value = "关键词",defaultValue = "",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/list")
    public ResponseResult getSayList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ){

        Page<Tag> tagIPage = tagService.page(keyword, false, false, currentPage, pageSize);

        return ResponseResult.success(tagIPage.getRecords(), tagIPage.getTotal());
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


}

