package xyz.pplax.pplaxblog.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.file.service.BlogFileService;
import xyz.pplax.pplaxblog.file.service.UserFileService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;

/**
 * 博客文件上传服务相关接口
 *
 * @author PPLAX
 * @date 2024/1/18 14:37
 */
@RestController
@RequestMapping("/blog")
@Api(value = "用户文件上传服务相关接口", tags = {"用户文件上传相关接口"})
@Slf4j
public class BlogFileController extends SuperController {

    @Autowired
    private BlogFileService blogFileService;


    @ApiOperation(value = "上传博客封面", notes = "上传博客封面")
    @PostMapping(value = "/coverImage")
    public String coverImageUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return toJson(blogFileService.blogCoverImageUpload(file));
    }


    @ApiOperation(value = "撰写博客时上传文件", notes = "撰写博客时上传文件")
    @PostMapping(value = "/attach/file")
    public String attachUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return toJson(blogFileService.attachUpload(file));
    }

    @ApiOperation(value = "撰写博客时上传图片", notes = "撰写博客时上传图片")
    @PostMapping(value = "/attach/image")
    public String imageAttachUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return toJson(blogFileService.imageAttachUpload(file));
    }
    @ApiOperation(value = "撰写博客时上传视频", notes = "撰写博客时上传视频")
    @PostMapping(value = "/attach/video")
    public String videoAttachUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return toJson(blogFileService.videoAttachUpload(file));
    }


}

