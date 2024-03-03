package xyz.pplax.pplaxblog.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.base.dto.PageDto;
import xyz.pplax.pplaxblog.xo.service.role.RoleService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;

/**
 * 用户表 Controller
 */
@RestController
@RequestMapping("/user")
@Api(value="用户Controller", tags={"用户Controller"})
public class UserController extends SuperController {

    @Autowired
    private UserService userService;

    private static final Logger log = LogManager.getLogger(UserController.class);

    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @GetMapping(value = "/list")
    public String getList(@RequestBody PageDto pageDto) {
        return success(userService.listByNickName(pageDto));
    }

    @ApiOperation(value="获取该用户的角色", notes="获取该用户的角色")
    @GetMapping(value = "/{userUid}/role")
    public String getUserRole(@PathVariable("userUid") String userUid) {
        return success(userService.getRoleWithMenu(userUid));
    }

}
