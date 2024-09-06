package xyz.pplax.pplaxblog.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.auth.service.POauthService;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.CaptchaDto;
import xyz.pplax.pplaxblog.xo.dto.EditPasswordDto;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.dto.RegisterDto;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/pOauth")
@Api(value="自定义登录认证相关Controller",tags={"自定义登录认证相关Controller"})
public class POauthController extends SuperController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private POauthService pOauthService;

    @ApiOperation(value="登录操作，获取token", notes="登录操作，获取token")
    @PostMapping(value = "/token")
    public ResponseResult login(HttpServletRequest httpServletRequest, @RequestBody LoginDto loginDto) throws HttpRequestMethodNotSupportedException {
        return ResponseResult.success(pOauthService.getToken(httpServletRequest, loginDto));
    }

    @ApiOperation(value="登出操作", notes="登出操作")
    @DeleteMapping("/token")
    public ResponseResult removeToken(HttpServletRequest httpServletRequest) {
        pOauthService.removeToken(httpServletRequest);
        return success();
    }

    @ApiOperation(value="对指定用户进行登出操作", notes="对指定用户进行登出操作")
    @DeleteMapping("/user/{userUid}/token")
    public ResponseResult removeToken(@PathVariable("userUid") String userUid) {
        pOauthService.removeToken(userUid);
        return success();
    }

    @ApiOperation(value="注册", notes="注册")
    @PostMapping("/register")
    public ResponseResult register( @RequestBody @Validated(value = {Insert.class}) RegisterDto registerDto) {
        pOauthService.register(registerDto);
        return success();
    }

    @ApiOperation(value = "修改密码", notes = "修改密码", response = String.class)
    @PutMapping(value = "/password")
    public ResponseResult editPassword(@RequestBody EditPasswordDto editPasswordDto) {
        pOauthService.editPassword(editPasswordDto);
        return success();
    }

    @ApiOperation(value = "获取图片验证码", notes = "获取图片验证码", response = String.class)
    @GetMapping(value = "/imageCaptcha")
    public ResponseResult getImageCaptcha() {
        return pOauthService.getImageCaptcha(new CaptchaDto());
    }

    @ApiOperation(value = "获取邮箱验证码", notes = "获取邮箱验证码", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email",value = "邮箱", defaultValue = "12345@qq.com",paramType = "query",dataType="String",required = true)
    })
    @GetMapping(value = "/emailCaptcha")
    public ResponseResult getEmailCaptcha(@RequestParam("email") String email) {

        // 将6位随机码发给消息队列
        rabbitTemplate.convertAndSend(MqConstants.EXCHANGE_DIRECT, MqConstants.PPLAX_EMAIL, email);

        return success();
    }
}
