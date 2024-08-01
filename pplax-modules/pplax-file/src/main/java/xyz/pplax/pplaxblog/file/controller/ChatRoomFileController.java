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
import xyz.pplax.pplaxblog.file.service.ChatRoomFileService;
import xyz.pplax.pplaxblog.file.service.MessageFileService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;

/**
 * 聊天室文件上传服务相关接口
 *
 * @author PPLAX
 * @date 2024/6/24 21:28
 */
@RestController
@RequestMapping("/chatRoom")
@Api(value = "聊天室文件上传服务相关接口", tags = {"聊天室文件上传服务相关接口"})
@Slf4j
public class ChatRoomFileController extends SuperController {

    @Autowired
    private ChatRoomFileService chatRoomFileService;

    @ApiOperation(value = "上传聊天室头像", notes = "上传聊天室头像")
    @PostMapping(value = "/avatar")
    public ResponseResult avatarImageUpload(@RequestParam("file") MultipartFile file) throws Exception {

        return chatRoomFileService.avatarUpload(file);
    }


}

