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
import xyz.pplax.pplaxblog.file.service.BlogFileService;
import xyz.pplax.pplaxblog.file.service.LinkFileService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;

/**
 * 友链文件上传服务相关接口
 *
 * @author PPLAX
 * @date 2024/3/23 12:04
 */
@RestController
@RequestMapping("/link")
@Api(value = "友链文件上传服务相关接口", tags = {"友链文件上传相关接口"})
@Slf4j
public class LinkFileController extends SuperController {

    @Autowired
    private LinkFileService linkFileService;

    @Value("${pplax.storage.mode:localStorage}")
    private String storageMode;

    @ApiOperation(value = "上传友链图标", notes = "上传友链图标")
    @PostMapping(value = "/iconImage")
    public String iconImageUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return toJson(linkFileService.iconImageUpload(storageMode, file));
    }


}

