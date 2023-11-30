package xyz.pplax.pplaxblog.admin.restapi;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.admin.dto.LoginDto;
import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.base.response.ResponseResult;
import xyz.pplax.pplaxblog.utils.ResultUtil;
import xyz.pplax.pplaxblog.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员表 RestApi
 */
@RestController
@RequestMapping("/admin")
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

	@ApiOperation(value="用户登录", notes="用户登录")
	@PostMapping("/login")
	public String login(HttpServletRequest request,
						@ApiParam(name = "username", value = "用户名", required = true) @RequestBody LoginDto loginDto) {

		String username = loginDto.getUsername();
		String password = loginDto.getPassword();

		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return ResultUtil.result(SysConf.ERROR, "账号或密码不能为空");
		}
		if(username.equals("admin") && password.equals("admin")) {
			Map<String, Object> result = new HashMap<>();
			result.put(SysConf.TOKEN, "admin");
			return JSON.toJSONString(ResponseResult.success(result));
		}
		return JSON.toJSONString(ResponseResult.error("error"));
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

