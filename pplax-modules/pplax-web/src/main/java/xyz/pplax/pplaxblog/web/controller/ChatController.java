package xyz.pplax.pplaxblog.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.feign.AdminFeignClient;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.LinkEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.LinkGetListDto;
import xyz.pplax.pplaxblog.xo.entity.ChatRoom;
import xyz.pplax.pplaxblog.xo.entity.Link;
import xyz.pplax.pplaxblog.xo.service.chatroom.ChatRoomService;
import xyz.pplax.pplaxblog.xo.service.link.LinkService;


/**
 * 聊天 Controller
 */
@RestController
@RequestMapping("/chat")
@Api(value="友链Controller", tags={"友链Controller"})
public class ChatController extends SuperController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private AdminFeignClient adminFeignClient;

    private static Logger log = LogManager.getLogger(ChatController.class);

    @ApiOperation(value="获得聊天室列表", notes="获得聊天室列表")
    @GetMapping("/room/list")
    public String getRoomList(@RequestParam("userUid") String userUid) {
        return toJson(ResponseResult.success(chatRoomService.getByUserUid(userUid)));
    }

    @ApiOperation(value="删除聊天室", notes="删除聊天室")
    @DeleteMapping("/room/{chatRoomUid}")
    public String deleteChatRoom(@PathVariable("chatRoomUid") String chatRoomUid) {
        return adminFeignClient.deleteChatRoom(chatRoomUid);
    }
}

