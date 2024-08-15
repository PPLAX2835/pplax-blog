package xyz.pplax.pplaxblog.admin.controller;


import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.MenuEditDto;
import xyz.pplax.pplaxblog.xo.entity.Menu;
import xyz.pplax.pplaxblog.xo.service.MenuService;

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
    public ResponseResult getTree() {
        List<Menu> tree = menuService.tree();

        return ResponseResult.success(tree);
    }

    @ApiOperation(value="更新菜单", notes="更新菜单")
    @PutMapping(value = "/{menuUid}")
    public ResponseResult updateMenu(@PathVariable("menuUid") String menuUid, @Validated(value = {Update.class}) @RequestBody MenuEditDto menuEditDto) {
        menuEditDto.setUid(menuUid);

        Boolean res = menuService.updateById(menuEditDto);

        return success();
    }

    @ApiOperation(value="新增菜单", notes="新增菜单")
    @PostMapping("")
    public ResponseResult add(@RequestBody @Validated(value = {Insert.class}) MenuEditDto menuEditDto) {

        Boolean res = menuService.save(menuEditDto);

        return success();
    }

    @ApiOperation(value="删除标签", notes="删除标签")
    @DeleteMapping("/{menuUid}")
    public ResponseResult delete(@PathVariable("menuUid") String menuUid) {
        Boolean res = menuService.removeById(menuUid);

        return success();
    }
}

