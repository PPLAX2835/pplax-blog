package xyz.pplax.pplaxblog.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.LinkEditDto;
import xyz.pplax.pplaxblog.xo.entity.Link;
import xyz.pplax.pplaxblog.xo.service.LinkService;


/**
 * 友链 Controller
 */
@RestController
@RequestMapping("/link")
@Api(value="友链Controller", tags={"友链Controller"})
public class LinkController extends SuperController {

    @Autowired
    private LinkService linkService;

    private static Logger log = LogManager.getLogger(LinkController.class);

    @ApiOperation(value="获得友链列表", notes="获得友链列表")
    @GetMapping("/list")
    public ResponseResult getLinkList(@RequestParam("currentPage") Long currentPage, @RequestParam("pageSize") Long pageSize) {
        Page<Link> linkIPage = linkService.page(null, EStatus.ENABLE.getStatus(), currentPage, pageSize);
        // 目前还不是根据用户查询
        return ResponseResult.success(linkIPage.getRecords(), linkIPage.getTotal());
    }

    @ApiOperation(value="申请友链", notes="申请友链")
    @PostMapping("")
    public ResponseResult add(@RequestBody @Validated(value = {Insert.class}) LinkEditDto linkEditDto) {

        Boolean res = linkService.apply(linkEditDto);

        return success();
    }
}

