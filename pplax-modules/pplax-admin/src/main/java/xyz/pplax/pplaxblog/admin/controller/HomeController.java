package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.DateUtil;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.constants.sql.*;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.service.*;

import java.util.*;

/**
 * 首页 Controller
 */
@RestController
@RequestMapping("/home")
@Api(value="首页Controller", tags={"首页Controller"})
public class HomeController extends SuperController {

    private static final Logger log = LogManager.getLogger(HomeController.class);

    @Autowired
    private SiteService siteService;

    @ApiOperation(value="初始化", notes="初始化")
    @GetMapping("/init")
    public ResponseResult init() {
        return success(siteService.getDashboardData());
    }

}

