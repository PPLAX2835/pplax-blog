package xyz.pplax.pplaxblog.admin.restapi;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.constants.BaseSysConstants;
import xyz.pplax.pplaxblog.commons.controller.SuperController;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.feign.auth.AuthFeignClient;
import xyz.pplax.pplaxblog.admin.global.SysConstants;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.constants.UserSQLConstants;
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

	@Value("${pplax.oauth.client-id}")
	private String clientId;

	@Value("${pplax.oauth.client-secret}")
	private String clientSecret;

	@ApiOperation(value="获取token", notes="获取token")
	@PostMapping("/token")
	public String getToken(HttpServletRequest httpServletRequest, @RequestBody LoginDto loginDto) {

		Map<String, String> map = JSONObject.parseObject(
				authFeignClient.getToken(
						clientId,
						clientSecret,
						BaseSysConstants.PASSWORD,
						loginDto.getUsername(),
						loginDto.getPassword()
				),
				new TypeReference<Map<String, String>>() {
				}
		);

		if (!StringUtils.isEmpty(map.get("access_token"))) {
			// 获取token成功，进行登录信息的储存

			// 记录登录ip、登录次数、登录时间
			QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
			userQueryWrapper.eq(UserSQLConstants.USERNAME, loginDto.getUsername());

			// 修改登录信息
			User user = userService.getOne(userQueryWrapper);
			user.setLastLoginTime(new Date());
			user.setLastLoginIp(IpUtils.getIpAddress(httpServletRequest));
			user.setLoginCount(user.getLoginCount() + 1);

			// 更新
			userService.updateById(user);

			// 返回结果脱敏
			map.remove(BaseSysConstants.UID);
			map.remove(BaseSysConstants.USER_INFO_UID);
			map.remove(BaseSysConstants.SALT);
		}

		log.info("返回结果");

		return success(map);
	}

	@ApiOperation(value = "用户信息", notes = "用户信息", response = String.class)
	@GetMapping(value = "/info")
	public String info(@ApiParam(name = "token", value = "token令牌",required = false) @RequestParam(name = "token", required = false) String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SysConstants.TOKEN, "admin");
		map.put(SysConstants.AVATAR, "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
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

