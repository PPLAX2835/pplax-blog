package xyz.pplax.pplaxblog.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.feign.AdminFeignClient;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.UserInfoEditDto;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.user.UserService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;


/**
 * 用户 Controller
 */
@RestController
@RequestMapping("/user")
@Api(value="用户Controller", tags={"用户Controller"})
public class UserController extends SuperController {

    @Autowired
    private AdminFeignClient adminFeignClient;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value="获取用户信息", notes="获取用户信息")
    @GetMapping(value = "/{userUid}/userInfo")
    public String getUserInfo (@PathVariable("userUid") String userUid) {
        User user = userService.getById(userUid);
        if (user != null) {
            user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
            user.sensitiveDataRemove();
        }

        return success(user);
    }


    @ApiOperation(value="修改用户信息", notes="修改用户信息")
    @PutMapping(value = "/{userUid}/userInfo")
    public String updateUserInfo (@PathVariable("userUid") String userUid, @RequestBody UserInfoEditDto userInfoEditDto) {
        if (userService.isUsernameExist(userInfoEditDto.getUsername())) {
            User user = userService.getById(userUid);
            if (!user.getUsername().equals(userInfoEditDto.getUsername())) {
                return toJson(ResponseResult.error(HttpStatus.USERNAME_EXIST));
            }
        }

        Boolean res = userInfoService.updateByUserUid(userUid, userInfoEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
