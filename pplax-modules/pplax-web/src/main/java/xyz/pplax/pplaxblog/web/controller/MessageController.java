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
import xyz.pplax.pplaxblog.xo.entity.Message;
import xyz.pplax.pplaxblog.xo.service.message.MessageService;

import javax.servlet.http.HttpServletRequest;


/**
 * 消息 Controller
 */
@RestController
@RequestMapping("/message")
@Api(value="消息Controller", tags={"消息Controller"})
public class MessageController extends SuperController {

    private static Logger log = LogManager.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private AdminFeignClient adminFeignClient;

    @ApiOperation(value = "获取消息列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取消息列表")
    @GetMapping("/list")
    public String getLeaveMessageList(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){
        return adminFeignClient.getLeaveMessageList(type, "", currentPage, pageSize);
    }

    @ApiOperation(value = "添加留言", httpMethod = "GET", response = ResponseResult.class, notes = "添加留言")
    @PostMapping("")
    public String addLeaveMessage(HttpServletRequest httpServletRequest, @RequestBody Message message){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = null;
        String userUid = null;
        if (!StringUtils.isBlank(authorization)) {
            accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        message.setUid(StringUtils.getUUID());
        message.setUserUid(userUid);
        message.setIp(IpUtils.getIpAddress(httpServletRequest));
        message.setAddress(IpUtils.getCityInfo(message.getIp()));

        boolean res = messageService.save(message);

        if (res) {
            return success();
        }

        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }


}

