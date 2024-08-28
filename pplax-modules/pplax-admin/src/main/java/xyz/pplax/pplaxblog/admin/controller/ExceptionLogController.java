package xyz.pplax.pplaxblog.admin.controller;

import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;

/**
* @description t_exception_log表 controller
* @author PPLAX
* @date Wed Aug 28 20:48:20 CST 2024
*/
@RestController
@RequestMapping("/exceptionLog")
@Api(value="t_exception_log表Controller", tags={"异常日志表"})
public class ExceptionLogController extends SuperController {

    private static final Logger log = LogManager.getLogger(ExceptionLogController.class);


}
