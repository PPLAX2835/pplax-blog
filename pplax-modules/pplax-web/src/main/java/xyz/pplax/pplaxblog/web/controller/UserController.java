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
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.constants.redis.AuthRedisConstants;
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

    @Autowired
    private RedisService redisService;

    @ApiOperation(value="获取自己的信息", notes="获取自己的信息")
    @GetMapping(value = "/userInfo")
    public ResponseResult getMyUserInfo (HttpServletRequest httpServletRequest) {
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
    public ResponseResult getUserInfo (@PathVariable("userUid") String userUid) {
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
    public ResponseResult updateUserInfo (HttpServletRequest httpServletRequest, @RequestBody UserInfoEditDto userInfoEditDto) {
        String userUid = getUserUid(httpServletRequest);

        // 检查验证码
        String code = redisService.getCacheObject(AuthRedisConstants.EMAIL_CODE + AuthRedisConstants.SEGMENTATION + userInfoEditDto.getEmail());
        if (StringUtils.isEmpty(code) || !code.equals(userInfoEditDto.getCode())) {
            return ResponseResult.error(HttpStatus.VALIDATION_CODE_INCORRECT);
        }

        Boolean res = userInfoService.updateByUserUid(userUid, userInfoEditDto);

        return getMyUserInfo(httpServletRequest);
    }

    @ApiOperation(value="修改个人空间背景", notes="修改个人空间背景")
    @PutMapping(value = "/userInfo/background")
    public ResponseResult updateBackground (HttpServletRequest httpServletRequest, @RequestBody UserInfoEditDto userInfoEditDto) {
        String userUid = getUserUid(httpServletRequest);

        // 只保留空间背景uid
        UserInfoEditDto userInfoEditDto1 = new UserInfoEditDto();
        userInfoEditDto1.setSpaceBackgroundPictureUid(userInfoEditDto.getSpaceBackgroundPictureUid());

        Boolean res = userInfoService.updateByUserUid(userUid, userInfoEditDto1);

        return getMyUserInfo(httpServletRequest);
    }

}
