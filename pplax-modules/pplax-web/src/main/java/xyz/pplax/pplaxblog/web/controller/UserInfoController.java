package xyz.pplax.pplaxblog.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.feign.AdminFeignClient;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;


/**
 * 用户信息 Controller
 */
@RestController
@RequestMapping("/user/{userUid}/userInfo")
@Api(value="用户信息Controller", tags={"用户信息Controller"})
public class UserInfoController extends SuperController {

    @Autowired
    private AdminFeignClient adminFeignClient;

    @ApiOperation(value="获取用户信息", notes="获取用户信息")
    @GetMapping(value = "")
    public String getUserInfo (@PathVariable("userUid") String userUid) {
        return adminFeignClient.getUserInfo(userUid);
    }

}
