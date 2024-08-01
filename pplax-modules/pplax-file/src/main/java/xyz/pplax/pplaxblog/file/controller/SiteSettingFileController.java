package xyz.pplax.pplaxblog.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.file.service.BlogFileService;
import xyz.pplax.pplaxblog.file.service.SiteSettingFileService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;

/**
 * 站点配置文件上传服务相关接口
 *
 * @author PPLAX
 * @date 2024/5/3 18:01
 */
@RestController
@RequestMapping("/siteSetting")
@Api(value = "站点配置文件上传服务相关接口", tags = {"站点配置文件上传服务相关接口"})
@Slf4j
public class SiteSettingFileController extends SuperController {

    @Autowired
    private SiteSettingFileService siteSettingFileService;

    @ApiOperation(value = "aboutMe上传图片", notes = "aboutMe上传图片")
    @PostMapping(value = "/aboutMe/image")
    public ResponseResult imageAttachUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return siteSettingFileService.siteSettingAboutMeImageAttachUpload(file);
    }

    @ApiOperation(value = "上传博客封面", notes = "上传博客封面")
    @PostMapping(value = "/logo")
    public ResponseResult coverImageUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return siteSettingFileService.logoUpload(file);
    }
}

