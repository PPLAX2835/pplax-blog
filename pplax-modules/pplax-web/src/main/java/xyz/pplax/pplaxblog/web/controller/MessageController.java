package xyz.pplax.pplaxblog.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.feign.AdminFeignClient;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.list.MessageGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Message;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.chatroom.ChatRoomService;
import xyz.pplax.pplaxblog.xo.service.message.MessageService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


/**
 * 消息 Controller
 */
@RestController
@RequestMapping("/message")
@Api(value="消息Controller", tags={"消息Controller"})
public class MessageController extends SuperController {

    private static Logger log = LogManager.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private AdminFeignClient adminFeignClient;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;


    @ApiOperation(value = "获取消息列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取消息列表")
    @GetMapping("/list")
    public String getLeaveMessageList(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "chatRoomUid", required = false) String chatRoomUid,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = null;
        String userUid = null;
        if (!StringUtils.isBlank(authorization)) {
            accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        IPage<Message> messageIPage = messageService.listChatMessage(userUid, chatRoomUid, currentPage, pageSize);

        return toJson(ResponseResult.success(messageIPage.getRecords(), messageIPage.getTotal()));
    }

    @ApiOperation(value = "添加消息", httpMethod = "GET", response = ResponseResult.class, notes = "添加消息")
    @PostMapping("")
    public String addLeaveMessage(HttpServletRequest httpServletRequest, @RequestBody Message message){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = null;
        String userUid = null;
        if (!StringUtils.isBlank(authorization)) {
            accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        message.setUid(StringUtils.getUUID());
        message.setUserUid(userUid);
        message.setIp(IpUtils.getIpAddress(httpServletRequest));
        message.setAddress(IpUtils.getCityInfo(message.getIp()));


        // 封装用户信息
        message.setUserInfo(userInfoService.getByUserUid(message.getUserUid()));
        if (message.getType() != 0) {
            // 只有聊天消息才封装
            // 封装已读用户
            String[] readUserUids = message.getReadUserUids().split(",");
            List<User> userList = userService.listByIds(Arrays.asList(readUserUids));
            for (User user : userList) {
                if (user != null) {
                    user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
                    user.sensitiveDataRemove();
                }
            }
            message.setReadUserList(userList);
            // 封装聊天室
            message.setChatRoom(chatRoomService.getById(message.getChatRoomUid()));
        }
        // 修改撤回记录
        if (message.getStatus() == EStatus.WITHDRAW.getStatus()) {
            message.setContent("该消息已被撤回");
        }

        boolean res = messageService.save(message);

        if (res) {
            return success(message);
        }

        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @ApiOperation(value = "已读操作", httpMethod = "GET", response = ResponseResult.class, notes = "已读操作")
    @GetMapping("/read")
    public String getLeaveMessageList(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "chatRoomUid") String chatRoomUid
    ){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = null;
        String userUid = null;
        if (!StringUtils.isBlank(authorization)) {
            accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        messageService.read(userUid, chatRoomUid);

        return success();
    }
}

