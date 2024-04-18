package xyz.pplax.pplaxblog.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.list.TagGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;
import xyz.pplax.pplaxblog.xo.service.site.SiteService;
import xyz.pplax.pplaxblog.xo.service.tag.TagService;

import javax.servlet.http.HttpServletRequest;


/**
 * 网站 Controller
 */
@RestController
@RequestMapping("/site")
@Api(value="网站Controller", tags={"网站Controller"})
public class WebSiteController extends SuperController {

    @Autowired
    private SiteService siteService;

    private static Logger log = LogManager.getLogger(WebSiteController.class);

    @ApiOperation(value="获取主页有关数据", httpMethod = "GET", notes="获取主页有关数据")
    @GetMapping("/homeData")
    public String getInfo() {
        return toJson(siteService.getHomeData());
    }

    @ApiOperation(value = "网站相关信息", httpMethod = "GET", response = ResponseResult.class, notes = "网站相关信息")
    @GetMapping("/info")
    public String getWebSiteInfo(){
        return toJson(siteService.getWebSiteInfo());
    }

    @ApiOperation(value="向网站报告，记录访问数据", notes="向网站报告，记录访问数据")
    @GetMapping("/report")
    public String report(HttpServletRequest httpServletRequest) {
        return toJson(siteService.report(httpServletRequest));
    }

}

