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
import xyz.pplax.pplaxblog.xo.dto.list.MessageGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Message;
import xyz.pplax.pplaxblog.xo.service.MessageService;

import java.util.List;

/**
 * 消息表 Controller
 */
@RestController
@RequestMapping("/message")
@Api(value="消息表Controller", tags={"消息表Controller"})
public class MessageController extends SuperController {

    private static final Logger log = LogManager.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @ApiOperation(value="获取消息列表", notes="获取消息列表")
    @GetMapping("/list")
    public String getList(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ) {
        // 封装
        MessageGetListDto messageGetListDto = new MessageGetListDto();
        messageGetListDto.setType(type);
        messageGetListDto.setKeyword(keyword);
        messageGetListDto.setCurrentPage(currentPage);
        messageGetListDto.setPageSize(pageSize);

        IPage<Message> messageIPage = messageService.list(messageGetListDto);

        return toJson(ResponseResult.success(messageIPage.getRecords(), messageIPage.getTotal()));
    }

    @ApiOperation(value="删除消息记录", notes="删除消息记录")
    @DeleteMapping("/{messageUid}")
    public String delete(@PathVariable("messageUid") String messageUid) {
        boolean res = messageService.removeById(messageUid);

        if (res) {
            return success();
        }

        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value = "批量删除消息", notes = "批量删除消息")
    @DeleteMapping(value = "")
    public String delete(@RequestBody List<String> messageUidList) {
        boolean res = messageService.removeByIds(messageUidList);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

