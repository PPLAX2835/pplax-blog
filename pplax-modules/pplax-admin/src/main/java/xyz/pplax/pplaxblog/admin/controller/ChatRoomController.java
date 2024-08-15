package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.entity.ChatRoom;
import xyz.pplax.pplaxblog.xo.service.ChatRoomService;

import java.util.List;

/**
 * 聊天室表 Controller
 */
@RestController
@RequestMapping("/chatRoom")
@Api(value="聊天室Controller", tags={"聊天室Controller"})
public class ChatRoomController extends SuperController {

    private static final Logger log = LogManager.getLogger(ChatRoomController.class);

    @Autowired
    private ChatRoomService chatRoomService;

    @ApiOperation(value="获取留言列表", notes="获取留言列表")
    @GetMapping("/list")
    public ResponseResult getList(
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ) {
        // 封装
        Page<ChatRoom> chatRoomIPage = chatRoomService.page(currentPage, pageSize);

        return ResponseResult.success(chatRoomIPage.getRecords(), chatRoomIPage.getTotal());
    }

    @ApiOperation(value="删除聊天室", notes="删除聊天室")
    @DeleteMapping("/{chatRoomUid}")
    public ResponseResult delete(@PathVariable("chatRoomUid") String chatRoomUid) {
        chatRoomService.removeById(chatRoomUid);

        return success();
    }

    @ApiOperation(value = "批量删除留言", notes = "批量删除留言")
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> chatRoomUidList) {
        chatRoomService.removeByIds(chatRoomUidList);

        return success();
    }
}

