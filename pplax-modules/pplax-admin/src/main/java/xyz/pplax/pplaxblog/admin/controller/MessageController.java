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

    @GetMapping("/list")
    @ApiOperation(value="获取消息列表", notes="获取消息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "消息类型", defaultValue = "",paramType = "query",dataType="Integer",required = false),
            @ApiImplicitParam(name = "keyword",value = "关键词",defaultValue = "",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    public ResponseResult getList(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ) {

        Page<Message> messageIPage = messageService.page(keyword, type, null, currentPage, pageSize);

        return ResponseResult.success(messageIPage.getRecords(), messageIPage.getTotal());
    }

    @DeleteMapping("/{messageUid}")
    @ApiOperation(value="删除消息记录", notes="删除消息记录")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "messageUid",
                    value = "消息uid",
                    defaultValue = "ba7da7000204470691f1ad4dfe85c440",
                    paramType = "path",
                    dataType="String",
                    required = true
            )
    })
    public ResponseResult delete(@PathVariable("messageUid") String messageUid) {
        messageService.removeById(messageUid);

        return success();
    }

    @DeleteMapping(value = "")
    @ApiOperation(value = "批量删除消息", notes = "批量删除消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "messageUidList",value = "消息uidList", defaultValue = "",dataType="List<String>",required = true)
    })
    public ResponseResult delete(@RequestBody List<String> messageUidList) {
        messageService.removeByIds(messageUidList);

        return success();
    }
}

