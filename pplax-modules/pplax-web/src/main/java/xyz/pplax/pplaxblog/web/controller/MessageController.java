package xyz.pplax.pplaxblog.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.ChatRoomEditDto;
import xyz.pplax.pplaxblog.xo.dto.edit.MessageEditDto;
import xyz.pplax.pplaxblog.xo.entity.ChatRoom;
import xyz.pplax.pplaxblog.xo.entity.Message;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.ChatRoomService;
import xyz.pplax.pplaxblog.xo.service.MessageService;
import xyz.pplax.pplaxblog.xo.service.UserService;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;

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
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;


    @ApiOperation(value = "获取留言列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取留言列表")
    @GetMapping("/leave/list")
    public ResponseResult getLeaveMessageList(
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){

        Page<Message> messageIPage = messageService.pageLeaveMessage(currentPage, pageSize);

        return ResponseResult.success(messageIPage.getRecords(), messageIPage.getTotal());
    }


    @ApiOperation(value = "添加留言", httpMethod = "POST", response = ResponseResult.class, notes = "添加留言")
    @PostMapping("/leave")
    public ResponseResult addLeaveMessage(HttpServletRequest httpServletRequest, @RequestBody MessageEditDto messageEditDto){

        String userUid = getUserUid(httpServletRequest);

        Message message = new Message();
        message.setContent(messageEditDto.getContent());
        message.setUid(StringUtils.getUUID());
        message.setType(0);
        message.setUserUid(userUid);
        message.setIp(IpUtils.getIpAddress(httpServletRequest));
        message.setAddress(IpUtils.getCityInfo(message.getIp()));

        rabbitTemplate.convertAndSend(MqConstants.EXCHANGE_DIRECT, MqConstants.PPLAX_LEAVE_MESSAGE, message);

        return success();
    }

    @ApiOperation(value="创建聊天室", notes="创建聊天室")
    @PostMapping("/room")
    public ResponseResult createChatRoom(HttpServletRequest httpServletRequest, @RequestBody ChatRoomEditDto chatRoomEditDto) {
        String userUid = getUserUid(httpServletRequest);

        chatRoomService.createChatRoom(
                userUid,
                chatRoomEditDto.getName(),
                chatRoomEditDto.getAvatarUid(),
                chatRoomEditDto.getType(),
                chatRoomEditDto.getMemberUids()
        );

        return success();
    }

    @ApiOperation(value="获得聊天室列表", notes="获得聊天室列表")
    @GetMapping("/room/list")
    public ResponseResult getRoomList(HttpServletRequest httpServletRequest, @RequestParam("isOwner") boolean isOwner) {
        String userUid = getUserUid(httpServletRequest);
        return ResponseResult.success(chatRoomService.listByUserUid(userUid, isOwner));
    }

    @ApiOperation(value="搜索聊天室", notes="搜索聊天室")
    @GetMapping("/room/search")
    public ResponseResult getRoomSearch(
            HttpServletRequest httpServletRequest,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ) {
        String userUid = getUserUid(httpServletRequest);

        Page<ChatRoom> chatRoomPage = chatRoomService.pageGroupChatNotInByName(userUid, keyword, currentPage, pageSize);
        return ResponseResult.success(chatRoomPage.getRecords(), chatRoomPage.getTotal());
    }

    @ApiOperation(value="加入聊天室", notes="加入聊天室")
    @PutMapping("/room/{roomUid}/join")
    public ResponseResult joinRoom(HttpServletRequest httpServletRequest, @PathVariable("roomUid") String roomUid) {
        String userUid = getUserUid(httpServletRequest);

        chatRoomService.joinChatRoom(userUid, roomUid);

        return success();
    }

    @ApiOperation(value="退出聊天室", notes="退出聊天室")
    @DeleteMapping("/room/{chatRoomUid}")
    public ResponseResult deleteChatRoom(HttpServletRequest httpServletRequest, @PathVariable("chatRoomUid") String chatRoomUid) {
        String userUid = getUserUid(httpServletRequest);

        chatRoomService.exitChatRoom(userUid, chatRoomUid);

        return success();
    }

    @ApiOperation(value="修改聊天室", notes="修改聊天室")
    @PutMapping("/room/{chatRoomUid}")
    public ResponseResult updateChatRoom(
            HttpServletRequest httpServletRequest,
            @PathVariable("chatRoomUid") String chatRoomUid,
            @RequestBody ChatRoomEditDto chatRoomEditDto
    ) {
        String userUid = getUserUid(httpServletRequest);

        ChatRoom chatRoom = chatRoomService.getById(chatRoomUid);
        if (chatRoom == null || !chatRoom.getOwnerUid().equals(userUid)) {
            return ResponseResult.error(HttpStatus.BAD_REQUEST);
        }

        chatRoom.setName(chatRoomEditDto.getName());
        chatRoom.setAvatarUid(chatRoomEditDto.getAvatarUid());

        chatRoomService.updateById(chatRoom);

        return success();
    }

    @ApiOperation(value="获取成员列表", notes="获取成员列表")
    @GetMapping("/room/{chatRoomUid}/member/list")
    public ResponseResult getChatRoomMemberList(
            @PathVariable("chatRoomUid") String chatRoomUid
    ) {
        return success(chatRoomService.listChatRoomMember(chatRoomUid));
    }

    @ApiOperation(value="踢出群成员", notes="踢出群成员")
    @DeleteMapping("/room/{chatRoomUid}/member/{memberUid}")
    public ResponseResult kickChatRoomMember(
            HttpServletRequest httpServletRequest,
            @PathVariable("chatRoomUid") String chatRoomUid,
            @PathVariable("memberUid") String memberUid
    ) {
        String userUid = getUserUid(httpServletRequest);

        chatRoomService.kickChatRoomMember(userUid, chatRoomUid, memberUid);

        return success();
    }

    @ApiOperation(value = "获取聊天记录", httpMethod = "GET", response = ResponseResult.class, notes = "获取聊天记录")
    @GetMapping("/room/{chatRoomUid}/chat/list")
    public ResponseResult getChatMessageList(
            HttpServletRequest httpServletRequest,
            @PathVariable(value = "chatRoomUid") String chatRoomUid,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){
        String userUid = getUserUid(httpServletRequest);

        Page<Message> messageIPage = messageService.pageChatMessage(userUid, chatRoomUid, currentPage, pageSize);

        return ResponseResult.success(messageIPage.getRecords(), messageIPage.getTotal());
    }

    @ApiOperation(value = "已读操作", httpMethod = "GET", response = ResponseResult.class, notes = "已读操作")
    @GetMapping("/room/{chatRoomUid}/chat/read")
    public ResponseResult readChatMessage(
            HttpServletRequest httpServletRequest,
            @PathVariable(value = "chatRoomUid") String chatRoomUid
    ){
        String userUid = getUserUid(httpServletRequest);

        messageService.read(userUid, chatRoomUid);

        return success();
    }

    @ApiOperation(value = "撤回消息", httpMethod = "DELETE", response = ResponseResult.class, notes = "撤回消息")
    @DeleteMapping("/room/{chatRoomUid}/chat/{chatMessageUid}")
    public ResponseResult withdrawChatMessage(
            HttpServletRequest httpServletRequest,
            @PathVariable(value = "chatRoomUid") String chatRoomUid,
            @PathVariable(value = "chatMessageUid") String chatMessageUid
    ){
        String userUid = getUserUid(httpServletRequest);

        messageService.withdraw(userUid, chatRoomUid, chatMessageUid);

        return success();
    }

    @ApiOperation(value = "添加聊天消息", httpMethod = "POST", response = ResponseResult.class, notes = "添加聊天消息")
    @PostMapping("/room/{chatRoomUid}/chat")
    public ResponseResult addChatMessage(
            HttpServletRequest httpServletRequest,
            @PathVariable("chatRoomUid") String chatRoomUid,
            @RequestBody Message message
    ){
        String userUid = getUserUid(httpServletRequest);

        message.setChatRoomUid(chatRoomUid);
        message.setType(1);
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

        rabbitTemplate.convertAndSend(MqConstants.EXCHANGE_DIRECT, MqConstants.PPLAX_CHAT_MESSAGE, message);

        return success(message);
    }
}

