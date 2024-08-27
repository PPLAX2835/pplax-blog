package xyz.pplax.pplaxblog.admin.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
@Api(value="菜单Controller", tags={"菜单Controller"})
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuUid",value = "菜单uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
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

    @ApiOperation(value="删除菜单", notes="删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuUid",value = "菜单uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{menuUid}")
    public ResponseResult delete(@PathVariable("menuUid") String menuUid) {
        Boolean res = menuService.removeById(menuUid);

        return success();
    }
}

