package xyz.pplax.pplaxblog.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.pplaxblog.feign.AdminFeignClient;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;


/**
 * 个人主页 Controller
 */
@RestController
@RequestMapping("/user/space")
@Api(value="个人主页Controller", tags={"个人主页Controller"})
public class SpaceController extends SuperController {

    @Autowired
    private AdminFeignClient adminFeignClient;



}
