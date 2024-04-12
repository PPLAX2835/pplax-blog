package xyz.pplax.pplaxblog.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.feign.AdminFeignClient;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.entity.LeaveMessage;
import xyz.pplax.pplaxblog.xo.service.leavemessage.LeaveMessageService;

import javax.servlet.http.HttpServletRequest;


/**
 * 留言 Controller
 */
@RestController
@RequestMapping("/leaveMessage")
@Api(value="留言Controller", tags={"留言Controller"})
public class LeaveMessageController extends SuperController {

    private static Logger log = LogManager.getLogger(LeaveMessageController.class);

    @Autowired
    private LeaveMessageService leaveMessageService;

    @Autowired
    private AdminFeignClient adminFeignClient;

    @ApiOperation(value = "获取留言列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取留言列表")
    @GetMapping("/list")
    public String getLeaveMessageList(
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){
        return adminFeignClient.getList("", currentPage, pageSize);
    }

    @ApiOperation(value = "添加留言", httpMethod = "GET", response = ResponseResult.class, notes = "添加留言")
    @PostMapping("")
    public String addLeaveMessage(HttpServletRequest httpServletRequest, @RequestBody LeaveMessage leaveMessage){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = null;
        String userUid = null;
        if (!StringUtils.isBlank(authorization)) {
            accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        leaveMessage.setUid(StringUtils.getUUID());
        leaveMessage.setUserUid(userUid);
        leaveMessage.setIp(IpUtils.getIpAddress(httpServletRequest));
        leaveMessage.setAddress(IpUtils.getCityInfo(leaveMessage.getIp()));

        boolean res = leaveMessageService.save(leaveMessage);

        if (res) {
            return success();
        }

        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }


}

