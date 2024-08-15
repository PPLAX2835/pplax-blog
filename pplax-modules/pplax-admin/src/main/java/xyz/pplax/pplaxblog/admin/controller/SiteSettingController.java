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
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.service.SiteSettingService;

import java.util.Map;

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
    public ResponseResult getList() {
        // 封装
        return ResponseResult.success(siteSettingService.list());
    }

    @ApiOperation(value="获取配置map形式", notes="获取配置map形式")
    @GetMapping("/map")
    public ResponseResult getMap() {
        // 封装
        return ResponseResult.success(siteSettingService.map());
    }

    @ApiOperation(value="编辑配置", notes="编辑配置")
    @PutMapping("/{siteSettingUid}")
    public ResponseResult updateById(@PathVariable("siteSettingUid") String siteSettingUid, @RequestBody @Validated(value = {Update.class}) SiteSettingEditDto siteSettingEditDto) {
        siteSettingEditDto.setUid(siteSettingUid);
        siteSettingService.updateById(siteSettingEditDto);

        return success();
    }

    @ApiOperation(value="编辑配置", notes="编辑配置")
    @PutMapping("")
    public ResponseResult updateByMap(@RequestBody Map<String, SiteSetting> data) {

        siteSettingService.updateByMap(data);

        return success();
    }

    @ApiOperation(value="新增配置", notes="新增配置")
    @PostMapping("")
    public ResponseResult add(@RequestBody @Validated(value = {Insert.class}) SiteSettingEditDto siteSettingEditDto) {

        siteSettingService.save(siteSettingEditDto);

        return success();
    }

    @ApiOperation(value="删除", notes="删除")
    @DeleteMapping("/{siteSettingUid}")
    public ResponseResult delete(@PathVariable("siteSettingUid") String siteSettingUid) {
        siteSettingService.removeById(siteSettingUid);

        return success();
    }
}

