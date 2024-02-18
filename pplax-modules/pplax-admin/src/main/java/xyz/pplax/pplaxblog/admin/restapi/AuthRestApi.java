package xyz.pplax.pplaxblog.admin.restapi;


import com.alibaba.fastjson.JSON;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.feign.auth.AuthFeignClient;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import xyz.pplax.pplaxblog.xo.dto.CaptchaDto;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.service.auth.AuthService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 登录认证相关 RestApi
 */
@RestController
@RequestMapping("/auth")
@Api(value="登录认证相关RestApi",tags={"AuthRestApi"})
public class AuthRestApi extends SuperController {

	private static final Logger log = LogManager.getLogger(AuthRestApi.class);

	@Autowired
	private AuthFeignClient authFeignClient;

	@Autowired
	private AuthService authService;

	@ApiOperation(value="获取token", notes="获取token")
	@PostMapping("/token")
	public String getToken(HttpServletRequest httpServletRequest, @RequestBody LoginDto loginDto) {

		return toJson(authService.getToken(httpServletRequest, loginDto));
	}

	@ApiOperation(value = "用户信息", notes = "用户信息", response = String.class)
	@GetMapping(value = "/info")
	public String info(HttpServletRequest httpServletRequest) {

		log.info("返回结果");
		return toJson(authService.info(httpServletRequest));
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

