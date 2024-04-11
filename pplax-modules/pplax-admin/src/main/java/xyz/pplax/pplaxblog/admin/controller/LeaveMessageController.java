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
import xyz.pplax.pplaxblog.xo.dto.list.CommentGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.LeaveMessageGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.entity.LeaveMessage;
import xyz.pplax.pplaxblog.xo.service.comment.CommentService;
import xyz.pplax.pplaxblog.xo.service.leavemessage.LeaveMessageService;

import java.util.List;

/**
 * 留言表 Controller
 */
@RestController
@RequestMapping("/leaveMessage")
@Api(value="留言Controller", tags={"留言Controller"})
public class LeaveMessageController extends SuperController {

    private static final Logger log = LogManager.getLogger(LeaveMessageController.class);

    @Autowired
    private LeaveMessageService leaveMessageService;

    @ApiOperation(value="获取留言列表", notes="获取留言列表")
    @GetMapping("/list")
    public String getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ) {
        // 封装
        LeaveMessageGetListDto leaveMessageGetListDto = new LeaveMessageGetListDto();
        leaveMessageGetListDto.setKeyword(keyword);
        leaveMessageGetListDto.setCurrentPage(currentPage);
        leaveMessageGetListDto.setPageSize(pageSize);

        IPage<LeaveMessage> leaveMessageIPage = leaveMessageService.list(leaveMessageGetListDto);

        return toJson(ResponseResult.success(leaveMessageIPage.getRecords(), leaveMessageIPage.getTotal()));
    }

    @ApiOperation(value="删除留言", notes="删除留言")
    @DeleteMapping("/{leaveMessageUid}")
    public String delete(@PathVariable("leaveMessageUid") String leaveMessageUid) {
        boolean res = leaveMessageService.removeById(leaveMessageUid);

        if (res) {
            return success();
        }

        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value = "批量删除留言", notes = "批量删除留言")
    @DeleteMapping(value = "")
    public String delete(@RequestBody List<String> leaveMessageUidList) {
        boolean res = leaveMessageService.removeByIds(leaveMessageUidList);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

