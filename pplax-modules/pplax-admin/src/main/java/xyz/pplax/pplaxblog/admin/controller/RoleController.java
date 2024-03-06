package xyz.pplax.pplaxblog.admin.controller;

import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.service.role.RoleService;


@RestController
@RequestMapping("/role")
@Api(value="角色Controller", tags={"角色Controller"})
public class RoleController extends SuperController {

    private static final Logger log = LogManager.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    /**
     * 获得角色列表，不包含菜单
     * @return
     */
    @GetMapping(value = "/list")
    public String getList() {
        return success(roleService.list());
    }

}
