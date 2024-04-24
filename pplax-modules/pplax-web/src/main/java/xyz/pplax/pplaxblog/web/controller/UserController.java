package xyz.pplax.pplaxblog.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.feign.AdminFeignClient;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.UserInfoEditDto;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.UserService;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;

import javax.servlet.http.HttpServletRequest;


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

    @ApiOperation(value="获取自己的信息", notes="获取自己的信息")
    @GetMapping(value = "/userInfo")
    public String getMyUserInfo (HttpServletRequest httpServletRequest) {
        String userUid = getUserUid(httpServletRequest);

        User user = userService.getById(userUid);
        if (user != null) {
            user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
            user.sensitiveDataRemove();
        }

        return success(user);
    }

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

    @ApiOperation(value="判断用户名是否存在", notes="判断用户名是否存在")
    @GetMapping(value = "/exist")
    public String isUsernameExist(@RequestParam("username") String username) {
        return adminFeignClient.isUsernameExist(username);
    }


    @ApiOperation(value="修改个人信息", notes="修改个人信息")
    @PutMapping(value = "/userInfo")
    public String updateUserInfo (HttpServletRequest httpServletRequest, @RequestBody UserInfoEditDto userInfoEditDto) {
        String userUid = null;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            String accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        Boolean res = userInfoService.updateByUserUid(userUid, userInfoEditDto);

        if (res) {
            return getMyUserInfo(httpServletRequest);
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
