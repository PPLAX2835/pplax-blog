package xyz.pplax.pplaxblog.file.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.file.service.UserFileService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.file.service.FileService;

/**
 * 用户文件上传服务相关接口
 *
 * @author PPLAX
 * @date 2024/1/18 14:37
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户文件上传服务相关接口", tags = {"用户文件上传相关接口"})
@Slf4j
public class UserFileController extends SuperController {

    @Autowired
    private UserFileService userFileService;

    @ApiOperation(value = "上传头像", notes = "上传头像")
    @PostMapping(value = "/avatar")
    public ResponseResult avatarUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return userFileService.avatarUpload(file);
    }

    @ApiOperation(value = "删除头像", notes = "删除头像")
    @DeleteMapping(value = "/avatar/{fileUid}")
    public ResponseResult avatarDelete( @PathVariable String fileUid) throws Exception {

        return userFileService.delete(fileUid);
    }

    @ApiOperation(value = "上传个人空间背景图片", notes = "上传个人空间背景图片")
    @PostMapping(value = "/spaceBackgroundPicture")
    public ResponseResult spaceBackgroundPictureUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return userFileService.spaceBackgroundPictureUpload(file);
    }
}

