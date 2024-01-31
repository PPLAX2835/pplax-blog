package xyz.pplax.pplaxblog.admin.restapi;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.base.global.BaseSysConf;
import xyz.pplax.pplaxblog.feign.auth.AuthFeignClient;
import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.commons.base.global.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import xyz.pplax.pplaxblog.utils.IpUtils;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.global.UserSQLConf;
import xyz.pplax.pplaxblog.xo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 登录认证相关 RestApi
 */
@RestController
@RequestMapping("/oauth")
@Api(value="登录认证相关RestApi",tags={"AuthRestApi"})
public class AuthRestApi {

	private static final Logger log = LogManager.getLogger(AuthRestApi.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AuthFeignClient authFeignClient;

	@Value("${pplax.oauth.client-id}")
	private String clientId;

	@Value("${pplax.oauth.client-secret}")
	private String clientSecret;

	@ApiOperation(value="获取token", notes="获取token")
	@PostMapping("/token")
	public String getToken(HttpServletRequest httpServletRequest, @RequestBody LoginDto loginDto) {

		String resp = JSON.toJSONString(ResponseResult.success(JSON.parseObject(authFeignClient.getToken(
				clientId,
				clientSecret,
				BaseSysConf.PASSWORD,
				loginDto.getUsername(),
				loginDto.getPassword()))));

		if (resp.contains("access_token")) {
			// 获取token成功，进行登录信息的储存

			// 记录登录ip、登录次数、登录时间
			QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
			userQueryWrapper.eq(UserSQLConf.USERNAME, loginDto.getUsername());

			// 修改登录信息
			User user = userService.getOne(userQueryWrapper);
			user.setLastLoginTime(new Date());
			user.setLastLoginIp(IpUtils.getIpAddress(httpServletRequest));
			user.setLoginCount(user.getLoginCount() + 1);

			// 更新
			userService.updateById(user);
		}

		log.info("返回结果");

		return resp;
	}

	@ApiOperation(value = "用户信息", notes = "用户信息", response = String.class)
	@GetMapping(value = "/info")
	public String info(@ApiParam(name = "token", value = "token令牌",required = false) @RequestParam(name = "token", required = false) String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SysConf.TOKEN, "admin");
		map.put(SysConf.AVATAR, "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
		List<String> list = new ArrayList<String>();
		list.add("admin");
		map.put("roles", list);
		return JSON.toJSONString(ResponseResult.success(map));
	}

	@ApiOperation(value = "退出登录", notes = "退出登录", response = String.class)
	@PostMapping(value = "/logout")
	public String logout(@ApiParam(name = "token", value = "token令牌",required = false) @RequestParam(name = "token", required = false) String token) {
		return JSON.toJSONString(ResponseResult.success());
	}

}

