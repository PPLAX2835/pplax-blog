package xyz.pplax.pplaxblog.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.UserInfoEditDto;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.UserService;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;


/**
 * 用户信息表 Controller
 */
@RestController
@RequestMapping("/user/{userUid}/userInfo")
@Api(value="用户信息Controller", tags={"用户信息Controller"})
public class UserInfoController extends SuperController {

    private static final Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @ApiOperation(value="获取用户信息", notes="获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userUid",value = "用户uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @GetMapping(value = "")
    public ResponseResult getUserInfo (@PathVariable("userUid") String userUid) {
        User user = userService.getById(userUid);
        if (user != null) {
            user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
            user.sensitiveDataRemove();
        }

        return success(user);
    }

    @ApiOperation(value="更新用户信息", notes="更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userUid",value = "用户uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @PutMapping(value = "")
    public ResponseResult updateUserInfo(@PathVariable("userUid") String userUid, @Validated(value = {Update.class}) @RequestBody UserInfoEditDto userInfoEditDto) {
        if (userService.isUsernameExist(userInfoEditDto.getUsername())) {
            User user = userService.getById(userUid);
            if (!user.getUsername().equals(userInfoEditDto.getUsername())) {
                return ResponseResult.error(HttpStatus.USERNAME_EXIST);
            }
        }

        Boolean res = userInfoService.updateByUserUid(userUid, userInfoEditDto);

        return success();
    }

}
