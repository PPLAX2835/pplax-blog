package xyz.pplax.pplaxblog.admin.controller;

import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
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

    @GetMapping(value = "")
    public String getUserInfo (@PathVariable("userUid") String userUid) {
        return success(userInfoService.getByUserUid(userUid));
    }

}
