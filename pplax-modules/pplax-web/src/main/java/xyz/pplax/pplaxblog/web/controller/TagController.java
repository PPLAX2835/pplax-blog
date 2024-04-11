package xyz.pplax.pplaxblog.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.list.SayGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.TagGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Say;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.service.say.SayService;
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
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){
        TagGetListDto tagGetListDto = new TagGetListDto();
        tagGetListDto.setCurrentPage(currentPage);
        tagGetListDto.setPageSize(pageSize);

        IPage<Tag> tagIPage = tagService.list(tagGetListDto);

        return toJson(ResponseResult.success(tagIPage.getRecords(), tagIPage.getTotal()));
    }
}

