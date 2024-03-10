package xyz.pplax.pplaxblog.admin.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.list.TagGetListDto;
import xyz.pplax.pplaxblog.xo.service.tag.TagService;

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
        // 封装
        TagGetListDto tagGetListDto = new TagGetListDto();
        tagGetListDto.setKeyword(keyword);
        tagGetListDto.setSortByClickCount(sortByClickCount);
        tagGetListDto.setSortByCites(sortByCites);
        tagGetListDto.setCurrentPage(currentPage);
        tagGetListDto.setPageSize(pageSize);

        return toJson(ResponseResult.success(
                tagService.list(tagGetListDto),
                tagService.count(tagGetListDto)
        ));
    }
}

