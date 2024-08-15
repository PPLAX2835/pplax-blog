package xyz.pplax.pplaxblog.admin.controller;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import xyz.pplax.pplaxblog.xo.component.auth.SuperAuthController;
import xyz.pplax.pplaxblog.xo.dto.EditPasswordDto;
import xyz.pplax.pplaxblog.xo.component.auth.AuthService;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录认证相关 Controller
 */
@RestController
@RequestMapping("/auth")
@Api(value="登录认证相关Controller",tags={"登录认证相关Controller"})
public class AuthController extends SuperAuthController {

	private static final Logger log = LogManager.getLogger(AuthController.class);

	@Autowired
	private AuthService authService;

	@ApiOperation(value = "修改密码", notes = "修改密码", response = String.class)
	@PutMapping(value = "/password")
	public ResponseResult editPassword(HttpServletRequest httpServletRequest, @RequestBody EditPasswordDto editPasswordDto) {
		return authService.editPassword(httpServletRequest, editPasswordDto);
	}

}

