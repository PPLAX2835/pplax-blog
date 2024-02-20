package xyz.pplax.pplaxblog.admin.controller;

import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.pplaxblog.xo.service.user.UserService;

/**
 * 博客表 Controller
 */
@RestController
@RequestMapping("/user")
@Api(value="用户Controller", tags={"用户Controller"})
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger log = LogManager.getLogger(UserController.class);


}
