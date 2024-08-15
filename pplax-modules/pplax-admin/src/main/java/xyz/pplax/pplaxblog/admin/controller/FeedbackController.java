package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public ResponseResult getList(
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ) {
        Page<Feedback> feedbackIPage = feedbackService.page(type, status, currentPage, pageSize);

        return ResponseResult.success(feedbackIPage.getRecords(), feedbackIPage.getTotal());
    }


    @ApiOperation(value="更新反馈", notes="更新反馈")
    @PutMapping("/{feedbackUid}")
    public ResponseResult updateFeedback(@PathVariable("feedbackUid") String feedbackUid, @RequestBody @Validated(value = {Update.class}) FeedbackEditDto feedbackEditDto) {
        Feedback feedback = new Feedback();
        feedback.setUid(feedbackUid);
        feedback.setType(feedbackEditDto.getType());
        feedback.setStatus(feedbackEditDto.getStatus());
        boolean res = feedbackService.updateById(feedback);

        return success();
    }

    @ApiOperation(value="删除反馈", notes="删除反馈")
    @DeleteMapping("/{feedbackUid}")
    public ResponseResult delete(@PathVariable("feedbackUid") String feedbackUid) {
        boolean res = feedbackService.removeById(feedbackUid);

        return success();
    }

    @ApiOperation(value = "批量删除反馈", notes = "批量删除反馈")
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> feedbackUidList) {
        boolean res = feedbackService.removeByIds(feedbackUidList);

        return success();
    }

}

