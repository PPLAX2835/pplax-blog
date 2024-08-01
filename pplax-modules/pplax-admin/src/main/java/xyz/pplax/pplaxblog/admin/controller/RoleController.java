package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.RoleEditDto;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.service.RoleService;

import java.util.List;


@RestController
@RequestMapping("/role")
@Api(value="角色Controller", tags={"角色Controller"})
public class RoleController extends SuperController {

    private static final Logger log = LogManager.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    /**
     * 获得角色列表
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseResult getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage", required = false) Long currentPage,
            @RequestParam(value = "pageSize", required = false) Long pageSize
    ) {
        if (StringUtils.isEmpty(keyword) && currentPage == null && pageSize == null) {
            List<Role> roleList = roleService.list();
            return ResponseResult.success(roleList, (long) roleList.size());
        }

        Page<Role> roleIPage = roleService.page(keyword, currentPage, pageSize);

        return ResponseResult.success(roleIPage.getRecords(), roleIPage.getTotal());
    }

    @ApiOperation(value="编辑角色", notes="编辑角色")
    @PutMapping("/{roleUid}")
    public ResponseResult update(@PathVariable("roleUid") String roleUid, @RequestBody @Validated(value = {Update.class}) RoleEditDto roleEditDto) {
        roleEditDto.setUid(roleUid);
        Boolean res = roleService.updateById(roleEditDto);

        roleService.preheat();

        if (res) {
            return success();
        }
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value="新增角色", notes="新增角色")
    @PostMapping("")
    public ResponseResult add(@RequestBody @Validated(value = {Insert.class}) RoleEditDto roleEditDto) {
        Boolean res = roleService.save(roleEditDto);

        roleService.preheat();

        if (res) {
            return success();
        }
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value="删除角色", notes="删除角色")
    @DeleteMapping("/{roleUid}")
    public ResponseResult delete(@PathVariable("roleUid") String roleUid) {
        Boolean res = roleService.removeById(roleUid);

        if (res) {
            return success();
        }
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "批量删除角色", notes = "批量删除角色")
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> roleUidList) {
        Boolean res = roleService.removeByIds(roleUidList);

        if (res) {
            return success();
        }
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
