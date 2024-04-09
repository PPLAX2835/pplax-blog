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
import xyz.pplax.pplaxblog.xo.entity.Say;
import xyz.pplax.pplaxblog.xo.service.blogsort.BlogSortService;
import xyz.pplax.pplaxblog.xo.service.say.SayService;


/**
 * 说说 Controller
 */
@RestController
@RequestMapping("/say")
@Api(value="说说Controller", tags={"说说Controller"})
public class SayController extends SuperController {

    private static Logger log = LogManager.getLogger(SayController.class);

    @Autowired
    private SayService sayService;

    @ApiOperation(value = "获取博客列表", httpMethod = "GET", response = ResponseResult.class, notes = "网站相关信息")
    @GetMapping("/list")
    public String getSayList(
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){
        SayGetListDto sayGetListDto = new SayGetListDto();
        sayGetListDto.setCurrentPage(currentPage);
        sayGetListDto.setPageSize(pageSize);

        IPage<Say> sayIPage = sayService.list(sayGetListDto);

        return toJson(ResponseResult.success(sayIPage.getRecords(), sayIPage.getTotal()));
    }
}

