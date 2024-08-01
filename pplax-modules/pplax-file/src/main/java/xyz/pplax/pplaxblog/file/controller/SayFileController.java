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
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.file.service.SayFileService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;

/**
 * 说说文件上传服务相关接口
 *
 * @author PPLAX
 * @date 2024/4/9 9:20
 */
@RestController
@RequestMapping("/say")
@Api(value = "说说文件上传服务相关接口", tags = {"说说文件上传服务相关接口"})
@Slf4j
public class SayFileController extends SuperController {

    @Autowired
    private SayFileService sayFileService;

    @ApiOperation(value = "上传友链图标", notes = "上传友链图标")
    @PostMapping(value = "/image")
    public ResponseResult imageUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return sayFileService.imageUpload(file);
    }


}

