package xyz.pplax.pplaxblog.xo.component.auth;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.validator.group.GetOne;
import xyz.pplax.pplaxblog.feign.AuthFeignClient;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.constants.redis.AuthRedisConstants;
import xyz.pplax.pplaxblog.xo.dto.CaptchaDto;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录认证相关 Controller
 */
@RestController
@Api(value="登录认证相关Controller",tags={"登录认证相关Controller"})
public class SuperAuthController extends SuperController {

	private static final Logger log = LogManager.getLogger(SuperAuthController.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private AuthFeignClient authFeignClient;

	@Autowired
	private RedisService redisService;

	@Autowired
	private AuthService authService;

	@ApiOperation(value="获取token", notes="获取token")
	@PostMapping("/token")
	public ResponseResult getToken(HttpServletRequest httpServletRequest, @RequestBody @Validated(value = {GetOne.class}) LoginDto loginDto) {

		return authService.getToken(httpServletRequest, loginDto);
	}

	@ApiOperation(value = "退出登录", notes = "退出登录", response = String.class)
	@DeleteMapping(value = "/token")
	public String logout(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
		String userUid = getUserUid(httpServletRequest);
		redisService.deleteObject(AuthRedisConstants.USER_TOKEN + AuthRedisConstants.SEGMENTATION + userUid);
		return authFeignClient.deleteToken(token);
	}

	@ApiOperation(value = "获取图片验证码", notes = "获取图片验证码", response = String.class)
	@GetMapping(value = "/imageCaptcha")
	public ResponseResult getImageCaptcha() {
		return authService.getImageCaptcha(new CaptchaDto());
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

