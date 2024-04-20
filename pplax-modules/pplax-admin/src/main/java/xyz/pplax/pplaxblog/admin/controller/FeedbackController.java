package xyz.pplax.pplaxblog.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.FeedbackEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.FeedbackGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Feedback;
import xyz.pplax.pplaxblog.xo.service.FeedbackService;

import java.util.List;

/**
 * 反馈表 Controller
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController extends SuperController {

    @Autowired
    private FeedbackService feedbackService;

    private static final Logger log = LogManager.getLogger(FeedbackController.class);

    @ApiOperation(value="获取反馈列表", notes="获取反馈列表")
    @GetMapping("/list")
    public String getList(
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ) {
        // 封装
        FeedbackGetListDto feedbackGetListDto = new FeedbackGetListDto();
        feedbackGetListDto.setStatus(status);
        feedbackGetListDto.setType(type);
        feedbackGetListDto.setCurrentPage(currentPage);
        feedbackGetListDto.setPageSize(pageSize);

        IPage<Feedback> feedbackIPage = feedbackService.list(feedbackGetListDto);

        return toJson(ResponseResult.success(feedbackIPage.getRecords(), feedbackIPage.getTotal()));
    }


    @ApiOperation(value="更新反馈", notes="更新反馈")
    @PutMapping("/{feedbackUid}")
    public String updateFeedback(@PathVariable("feedbackUid") String feedbackUid, @RequestBody @Validated(value = {Update.class}) FeedbackEditDto feedbackEditDto) {
        Feedback feedback = new Feedback();
        feedback.setUid(feedbackUid);
        feedback.setType(feedbackEditDto.getType());
        feedback.setStatus(feedbackEditDto.getStatus());
        Boolean res = feedbackService.updateById(feedback);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value="删除反馈", notes="删除反馈")
    @DeleteMapping("/{feedbackUid}")
    public String delete(@PathVariable("feedbackUid") String feedbackUid) {
        boolean res = feedbackService.removeById(feedbackUid);

        if (res) {
            return success();
        }

        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value = "批量删除反馈", notes = "批量删除反馈")
    @DeleteMapping(value = "")
    public String delete(@RequestBody List<String> feedbackUidList) {
        boolean res = feedbackService.removeByIds(feedbackUidList);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}

