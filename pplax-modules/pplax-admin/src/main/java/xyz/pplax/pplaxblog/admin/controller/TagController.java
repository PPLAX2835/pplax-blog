package xyz.pplax.pplaxblog.admin.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.xo.service.tag.TagService;

/**
 * 标签表 Controller
 */
@RestController
@RequestMapping("/tag")
@Api(value="标签Controller", tags={"标签Controller"})
public class TagController {
    @Autowired
    private TagService tagService;

    private static Logger log = LogManager.getLogger(TagController.class);

}

