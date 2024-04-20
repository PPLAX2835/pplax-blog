package xyz.pplax.pplaxblog.admin.controller;

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
import xyz.pplax.pplaxblog.xo.dto.edit.SiteSettingEditDto;
import xyz.pplax.pplaxblog.xo.service.SiteSettingService;

/**
 * 站点设置 Controller
 */
@RestController
@RequestMapping("/siteSetting")
@Api(value="站点设置", tags={"站点设置"})
public class SiteSettingController extends SuperController {

    @Autowired
    private SiteSettingService siteSettingService;

    private static Logger log = LogManager.getLogger(SiteSettingController.class);


    @ApiOperation(value="获取配置列表", notes="获取配置列表")
    @GetMapping("/list")
    public String getList() {
        // 封装
        return toJson(ResponseResult.success(siteSettingService.list()));
    }

    @ApiOperation(value="编辑配置", notes="编辑配置")
    @PutMapping("/{siteSettingUid}")
    public String update(@PathVariable("siteSettingUid") String siteSettingUid, @RequestBody @Validated(value = {Update.class}) SiteSettingEditDto siteSettingEditDto) {
        siteSettingEditDto.setUid(siteSettingUid);
        Boolean res = siteSettingService.updateById(siteSettingEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value="新增配置", notes="新增配置")
    @PostMapping("")
    public String add(@RequestBody @Validated(value = {Insert.class}) SiteSettingEditDto siteSettingEditDto) {

        Boolean res = siteSettingService.save(siteSettingEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value="删除", notes="删除")
    @DeleteMapping("/{siteSettingUid}")
    public String delete(@PathVariable("siteSettingUid") String siteSettingUid) {
        boolean res = siteSettingService.removeById(siteSettingUid);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

