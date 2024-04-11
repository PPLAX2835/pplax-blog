package xyz.pplax.pplaxblog.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.list.ChatMessageGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.ChatRoomGetListDto;
import xyz.pplax.pplaxblog.xo.entity.ChatMessage;
import xyz.pplax.pplaxblog.xo.service.chatmessage.ChatMessageService;

import java.util.List;

/**
 * 聊天记录表 Controller
 */
@RestController
@RequestMapping("/chatMessage")
@Api(value="聊天记录Controller", tags={"聊天记录Controller"})
public class ChatMessageController extends SuperController {

    private static final Logger log = LogManager.getLogger(ChatMessageController.class);

    @Autowired
    private ChatMessageService chatMessageService;

    @ApiOperation(value="获取聊天记录列表", notes="获取聊天记录列表")
    @GetMapping("/list")
    public String getList(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ) {
        // 封装
        ChatMessageGetListDto chatMessageGetListDto = new ChatMessageGetListDto();
        chatMessageGetListDto.setKeyword(keyword);
        chatMessageGetListDto.setCurrentPage(currentPage);
        chatMessageGetListDto.setPageSize(pageSize);

        IPage<ChatMessage> chatMessageIPage = chatMessageService.list(chatMessageGetListDto);

        return toJson(ResponseResult.success(chatMessageIPage.getRecords(), chatMessageIPage.getTotal()));
    }

    @ApiOperation(value="删除聊天记录", notes="删除聊天记录")
    @DeleteMapping("/{chatMessageUid}")
    public String delete(@PathVariable("chatMessageUid") String chatMessageUid) {
        boolean res = chatMessageService.removeById(chatMessageUid);

        if (res) {
            return success();
        }

        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value = "批量删除聊天记录", notes = "批量删除聊天记录")
    @DeleteMapping(value = "")
    public String delete(@RequestBody List<String> chatMessageUidList) {
        boolean res = chatMessageService.removeByIds(chatMessageUidList);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

