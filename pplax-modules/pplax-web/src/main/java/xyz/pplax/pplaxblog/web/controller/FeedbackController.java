package xyz.pplax.pplaxblog.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.entity.Feedback;
import xyz.pplax.pplaxblog.xo.service.FeedbackService;

import javax.servlet.http.HttpServletRequest;


/**
 * 反馈 Controller
 */
@RestController
@RequestMapping("/feedback")
@Api(value="反馈Controller", tags={"反馈Controller"})
public class FeedbackController extends SuperController {

    private static Logger log = LogManager.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;


    @ApiOperation(value = "添加反馈", httpMethod = "POST", response = ResponseResult.class, notes = "添加反馈")
    @PostMapping("")
    public ResponseResult addLeaveMessage(HttpServletRequest httpServletRequest, @RequestBody Feedback feedback){

        String userUid = getUserUid(httpServletRequest);
        feedback.setUserUid(userUid);
        feedback.setStatus(EStatus.UNRESOLVED.getStatus());

        boolean save = feedbackService.save(feedback);

        if (save) {
            return success();
        }
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

