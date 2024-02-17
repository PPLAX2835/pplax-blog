package xyz.pplax.pplaxblog.admin.restapi;


import com.alibaba.fastjson.JSON;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.controller.SuperController;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.feign.auth.AuthFeignClient;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.service.auth.AuthService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 登录认证相关 RestApi
 */
@RestController
@RequestMapping("/oauth")
@Api(value="登录认证相关RestApi",tags={"AuthRestApi"})
public class AuthRestApi extends SuperController {

	private static final Logger log = LogManager.getLogger(AuthRestApi.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AuthFeignClient authFeignClient;

	@Autowired
	private AuthService authService;

	@Value("${pplax.oauth.client-id}")
	private String clientId;

	@Value("${pplax.oauth.client-secret}")
	private String clientSecret;

	@ApiOperation(value="获取token", notes="获取token")
	@PostMapping("/token")
	public String getToken(HttpServletRequest httpServletRequest, @RequestBody LoginDto loginDto) {

		Map<String, String> map = authService.getToken(httpServletRequest, loginDto);

		if (map == null) {
			log.warn("token获取失败");
			return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
		}

		log.info("返回结果");
		return success(map);
	}

	@ApiOperation(value = "用户信息", notes = "用户信息", response = String.class)
	@GetMapping(value = "/info")
	public String info(HttpServletRequest httpServletRequest) {

		log.info("返回结果");
		return JSON.toJSONString(ResponseResult.success(authService.info(httpServletRequest)));
	}

	@ApiOperation(value = "退出登录", notes = "退出登录", response = String.class)
	@DeleteMapping(value = "/token")
	public String logout(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
		return authFeignClient.deleteToken(token);
	}

}

