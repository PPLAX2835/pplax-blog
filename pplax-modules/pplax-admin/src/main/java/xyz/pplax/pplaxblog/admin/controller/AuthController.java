package xyz.pplax.pplaxblog.admin.controller;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.validator.group.Delete;
import xyz.pplax.pplaxblog.commons.validator.group.GetOne;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.feign.auth.AuthFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import xyz.pplax.pplaxblog.xo.dto.CaptchaDto;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.service.auth.AuthService;

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

	@ApiOperation(value="获取token", notes="获取token")
	@PostMapping("/token")
	public String getToken(HttpServletRequest httpServletRequest, @RequestBody @Validated(value = {GetOne.class}) LoginDto loginDto) {

		return toJson(authService.getToken(httpServletRequest, loginDto));
	}

	@ApiOperation(value = "退出登录", notes = "退出登录", response = String.class)
	@DeleteMapping(value = "/token")
	public String logout(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
		return authFeignClient.deleteToken(token);
	}

	@ApiOperation(value = "获取验证码", notes = "获取验证码", response = String.class)
	@GetMapping(value = "/captcha")
	public String getCaptcha() {
		return toJson(authService.getCaptcha(new CaptchaDto()));
	}

}

