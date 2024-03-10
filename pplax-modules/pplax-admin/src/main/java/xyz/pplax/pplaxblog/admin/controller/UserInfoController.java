package xyz.pplax.pplaxblog.admin.controller;

import io.swagger.annotations.Api;
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
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;


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

    @ApiOperation(value="获取用户信息", notes="获取用户信息")
    @GetMapping(value = "")
    public String getUserInfo (@PathVariable("userUid") String userUid) {
        return success(userInfoService.getByUserUid(userUid));
    }

    @ApiOperation(value="更新用户信息", notes="更新用户信息")
    @PutMapping(value = "")
    public String updateUserInfo(@PathVariable("userUid") String userUid,@Validated(value = {Update.class}) @RequestBody UserInfoEditDto userInfoEditDto) {
        Boolean res = userInfoService.updateByUserUid(userUid, userInfoEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
