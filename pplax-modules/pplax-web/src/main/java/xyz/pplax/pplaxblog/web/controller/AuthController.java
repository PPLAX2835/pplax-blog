package xyz.pplax.pplaxblog.web.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.commons.validator.group.GetOne;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.feign.AuthFeignClient;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.CaptchaDto;
import xyz.pplax.pplaxblog.xo.dto.EditPasswordDto;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.component.auth.AuthService;
import xyz.pplax.pplaxblog.xo.dto.RegisterDto;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录认证相关 Controller
 */
@RestController
@RequestMapping("/auth")
@Api(value="登录认证相关Controller",tags={"登录认证相关Controller"})
public class AuthController extends SuperController {

	private static final Logger log = LogManager.getLogger(AuthController.class);

	@Autowired
	private AuthFeignClient authFeignClient;

	@Autowired
	private AuthService authService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@ApiOperation(value="获取token", notes="获取token")
	@PostMapping("/token")
	public String getToken(HttpServletRequest httpServletRequest, @RequestBody @Validated(value = {GetOne.class}) LoginDto loginDto) {

		return toJson(authService.getToken(httpServletRequest, loginDto));
	}

	@ApiOperation(value="注册", notes="注册")
	@PostMapping("/register")
	public String register( @RequestBody @Validated(value = {Insert.class}) RegisterDto registerDto) {
		return toJson(authService.register(registerDto));
	}

	@ApiOperation(value = "退出登录", notes = "退出登录", response = String.class)
	@DeleteMapping(value = "/token")
	public String logout(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
		return authFeignClient.deleteToken(token);
	}

	@ApiOperation(value = "获取图片验证码", notes = "获取图片验证码", response = String.class)
	@GetMapping(value = "/imageCaptcha")
	public String getImageCaptcha() {
		return toJson(authService.getImageCaptcha(new CaptchaDto()));
	}

	@ApiOperation(value = "获取邮箱验证码", notes = "获取邮箱验证码", response = String.class)
	@GetMapping(value = "/emailCaptcha")
	public String getEmailCaptcha(@RequestParam("email") String email) {

		// 将6位随机码发给消息队列
		rabbitTemplate.convertAndSend(MqConstants.EXCHANGE_DIRECT, MqConstants.PPLAX_EMAIL, email);

		return success();
	}

	@ApiOperation(value = "修改密码", notes = "修改密码", response = String.class)
	@PutMapping(value = "/password")
	public String editPassword(HttpServletRequest httpServletRequest, @RequestBody EditPasswordDto editPasswordDto) {
		return toJson(authService.editPassword(httpServletRequest, editPasswordDto));
	}

}

