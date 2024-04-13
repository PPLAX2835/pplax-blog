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
import xyz.pplax.pplaxblog.file.service.MessageFileService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;

/**
 * 消息文件上传服务相关接口
 *
 * @author PPLAX
 * @date 2024/3/23 12:04
 */
@RestController
@RequestMapping("/message")
@Api(value = "消息文件上传服务相关接口", tags = {"消息文件上传服务相关接口"})
@Slf4j
public class MessageFileController extends SuperController {

    @Autowired
    private MessageFileService messageFileService;

    @Value("${pplax.storage.mode:localStorage}")
    private String storageMode;

    @ApiOperation(value = "上传消息图片", notes = "上传消息图片")
    @PostMapping(value = "/image")
    public String iconImageUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return toJson(messageFileService.imageUpload(storageMode, file));
    }


}

