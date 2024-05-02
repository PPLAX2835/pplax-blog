package xyz.pplax.pplaxblog.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.file.service.SiteFileService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;

/**
 * 站点文件上传服务相关接口
 *
 * @author PPLAX
 * @date 2024/3/31 16:07
 */
@RestController
@RequestMapping("/site")
@Api(value = "站点文件上传服务相关接口", tags = {"站点文件上传服务相关接口"})
@Slf4j
public class SiteFileController extends SuperController {

    @Autowired
    private SiteFileService siteFileService;


    @ApiOperation(value = "上传博客封面", notes = "上传博客封面")
    @PostMapping(value = "/logo")
    public String coverImageUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return toJson(siteFileService.logoUpload(file));
    }

}
