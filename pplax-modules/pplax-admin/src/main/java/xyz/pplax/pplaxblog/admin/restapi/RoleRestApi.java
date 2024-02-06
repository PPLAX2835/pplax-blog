package xyz.pplax.pplaxblog.admin.restapi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.controller.SuperController;
import xyz.pplax.pplaxblog.xo.service.role.RoleService;


@RestController
@RequestMapping("/role")
@Api(value="角色RestApi", tags={"RoleRestApi"})
public class RoleRestApi extends SuperController {

    private static final Logger log = LogManager.getLogger(RoleRestApi.class);

    @Autowired
    RoleService roleService;


    /**
     * 根据uid查询角色
     * @param uid
     * @return
     */
    @ApiOperation(value="根据uid查询角色", notes="根据uid查询角色", response = String.class)
    @GetMapping("/{uid}")
    public String getByUid(
            @ApiParam(name = "uid", value = "唯一标识符",required = true) @PathVariable String uid
    ) {

        log.info("返回结果");
        return toJson(roleService.getById(uid));
    }

}
