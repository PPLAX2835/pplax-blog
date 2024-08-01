package xyz.pplax.pplaxblog.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.service.BlogService;

import javax.servlet.http.HttpServletRequest;


/**
 * 归档 Controller
 */
@RestController
@RequestMapping("/archive")
@Api(value="归档Controller", tags={"归档Controller"})
public class ArchiveController extends SuperController {

    @Autowired
    private BlogService blogService;

    private static Logger log = LogManager.getLogger(ArchiveController.class);

    @ApiOperation(value="获得归档列表", notes="获得归档列表")
    @GetMapping("")
    public ResponseResult getArchive(HttpServletRequest httpServletRequest) {

        String userUid = getUserUid(httpServletRequest);

        // 目前还不是根据用户查询
        return blogService.archive(userUid);
    }

}

