package xyz.pplax.pplaxblog.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.FeedbackEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.FeedbackGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Feedback;
import xyz.pplax.pplaxblog.xo.entity.Menu;
import xyz.pplax.pplaxblog.xo.service.feedback.FeedbackService;
import xyz.pplax.pplaxblog.xo.service.menu.MenuService;

import java.util.List;

/**
 * 菜单表 Controller
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends SuperController {

    @Autowired
    private MenuService menuService;

    private static final Logger log = LogManager.getLogger(MenuController.class);

    @ApiOperation(value="获取菜单树", notes="获取菜单树")
    @GetMapping("/tree")
    public String getTree() {
        List<Menu> tree = menuService.tree();

        return toJson(ResponseResult.success(tree));
    }

}

