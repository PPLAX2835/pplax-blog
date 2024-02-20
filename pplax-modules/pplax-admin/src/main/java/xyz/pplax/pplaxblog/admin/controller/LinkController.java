package xyz.pplax.pplaxblog.admin.controller;

import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.xo.service.link.LinkService;

@RestController
@RequestMapping("/link")
@Api(value="友情链接Controller", tags={"友情链接Controller"})
public class LinkController {

    @Autowired
    private LinkService linkService;

    private static final Logger log = LogManager.getLogger(LinkController.class);

}
