package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
        @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/list")
    public ResponseResult getList(
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ) {
        // 封装
        Page<ChatRoom> chatRoomIPage = chatRoomService.page(currentPage, pageSize);

        return ResponseResult.success(chatRoomIPage.getRecords(), chatRoomIPage.getTotal());
    }

    @ApiOperation(value="删除聊天室", notes="删除聊天室")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "chatRoomUid",value = "聊天室uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{chatRoomUid}")
    public ResponseResult delete(@PathVariable("chatRoomUid") String chatRoomUid) {
        chatRoomService.removeById(chatRoomUid);

        return success();
    }

    @ApiOperation(value = "批量删除留言", notes = "批量删除留言")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "chatRoomUidList",value = "聊天室uidList", defaultValue = "",dataType="List<String>",required = true)
    })
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> chatRoomUidList) {
        chatRoomService.removeByIds(chatRoomUidList);

        return success();
    }
}

