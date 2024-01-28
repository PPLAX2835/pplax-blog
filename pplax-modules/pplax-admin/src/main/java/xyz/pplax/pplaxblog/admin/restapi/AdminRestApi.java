package xyz.pplax.pplaxblog.admin.restapi;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.admin.feign.auth.AuthFeignClient;
import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.commons.base.global.BaseSysConf;
import xyz.pplax.pplaxblog.commons.base.global.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 管理员表 RestApi
 */
@RestController
@RequestMapping("${pplax.api.basePath}/admin")
@Api(value="管理员RestApi",tags={"AdminRestApi"})
public class AdminRestApi {

//	@Autowired
//	AdminService adminService;
//
//	private static Logger log = LogManager.getLogger(AdminRestApi.class);
//
//	@ApiOperation(value="获取管理员列表", notes="获取管理员列表")
//	@GetMapping("/getList")
//	public String getList(HttpServletRequest request,
//						  @ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
//						  @ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize) {
//
//		QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
//		Page<Admin> page = new Page<>();
//		page.setCurrent(currentPage);
//		page.setSize(pageSize);
//		IPage<Admin> pageList = adminService.page(page, queryWrapper);
//		List<Admin> list = pageList.getRecords();
//		log.info(list.toString());
//		return ResultUtil.result(SysConf.SUCCESS, list);
//	}

	@Autowired
	private AuthFeignClient authFeignClient;

	@Value("${pplax.sso.admin.client-id}")
	private String clientId;

	@Value("${pplax.sso.admin.client-secret}")
	private String clientSecret;

	@ApiOperation(value="获取token", notes="获取token")
	@PostMapping("/oauth/token")
	public String getToken(HttpServletRequest httpServletRequest, @RequestBody LoginDto loginDto) {


//		// 记录登录ip、登录次数、登录时间
//		User user = new User();
//		user.setUid(authorityMap.get(BaseSysConf.UID));     // 存uid
//		user.setLastLoginTime(new Date());
//		System.out.println(JSON.toJSONString(authentication.getAuthorities()));
////            user.setLastLoginIp(userDetails.g);

		return JSON.toJSONString(ResponseResult.success(JSON.parseObject(authFeignClient.getToken(
				clientId,
				clientSecret,
				BaseSysConf.PASSWORD,
				loginDto.getUsername(),
				loginDto.getPassword()))));
	}

	@ApiOperation(value = "用户信息", notes = "用户信息", response = String.class)
	@GetMapping(value = "/oauth/info")
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
	@PostMapping(value = "/oauth/logout")
	public String logout(@ApiParam(name = "token", value = "token令牌",required = false) @RequestParam(name = "token", required = false) String token) {
		return JSON.toJSONString(ResponseResult.success());
	}

}

