package xyz.pplax.pplaxblog.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.feign.sitesetting.SiteSettingFeignClient;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.service.sitesetting.SiteSettingService;


/**
 * 网站信息 Controller
 */
@RestController
@RequestMapping("/site")
@Api(value="网站信息Controller", tags={"网站信息Controller"})
public class SiteController extends SuperController {

    private static Logger log = LogManager.getLogger(SiteController.class);

    @Autowired
    private SiteSettingService siteSettingService;

    @ApiOperation(value="获取网站信息", notes="获取网站信息")
    @GetMapping("")
    public String getList() {
        return success(siteSettingService.map());
    }

}

