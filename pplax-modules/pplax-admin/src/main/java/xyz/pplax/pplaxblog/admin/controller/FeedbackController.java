package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
@Api(value="反馈Controller", tags={"反馈Controller"})
public class FeedbackController extends SuperController {

    @Autowired
    private FeedbackService feedbackService;

    private static final Logger log = LogManager.getLogger(FeedbackController.class);

    @ApiOperation(value="获取反馈列表", notes="获取反馈列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "状态",defaultValue = "",paramType = "query",dataType="Integer",required = false),
            @ApiImplicitParam(name = "type",value = "反馈类型",defaultValue = "",paramType = "query",dataType="Integer",required = false),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/list")
    public ResponseResult getList(
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ) {
        Page<Feedback> feedbackIPage = feedbackService.page(type, status, currentPage, pageSize);

        return ResponseResult.success(feedbackIPage.getRecords(), feedbackIPage.getTotal());
    }


    @ApiOperation(value="更新反馈", notes="更新反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedbackUid",value = "反馈uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedbackUid",value = "反馈uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{feedbackUid}")
    public ResponseResult delete(@PathVariable("feedbackUid") String feedbackUid) {
        boolean res = feedbackService.removeById(feedbackUid);

        return success();
    }

    @ApiOperation(value = "批量删除反馈", notes = "批量删除反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedbackUidList",value = "反馈uidList", defaultValue = "",dataType="List<String>",required = true)
    })
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> feedbackUidList) {
        boolean res = feedbackService.removeByIds(feedbackUidList);

        return success();
    }

}

