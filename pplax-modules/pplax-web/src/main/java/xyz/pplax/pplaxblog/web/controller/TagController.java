package xyz.pplax.pplaxblog.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
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
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.TagEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.TagGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.service.tag.TagService;


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
    @GetMapping("/list")
    public String getSayList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){
        TagGetListDto tagGetListDto = new TagGetListDto();
        tagGetListDto.setKeyword(keyword);
        tagGetListDto.setCurrentPage(currentPage);
        tagGetListDto.setPageSize(pageSize);

        IPage<Tag> tagIPage = tagService.list(tagGetListDto);

        return toJson(ResponseResult.success(tagIPage.getRecords(), tagIPage.getTotal()));
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


}

