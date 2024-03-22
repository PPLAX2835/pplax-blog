package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
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
import xyz.pplax.pplaxblog.xo.dto.edit.RoleEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.RoleGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.service.role.RoleService;

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
    public String getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage", required = false) Long currentPage,
            @RequestParam(value = "pageSize", required = false) Long pageSize
    ) {
        // 封装
        RoleGetListDto roleGetListDto = new RoleGetListDto();
        roleGetListDto.setCurrentPage(currentPage == null ? 0L : currentPage);
        roleGetListDto.setPageSize(pageSize == null ? 50L : pageSize);
        roleGetListDto.setKeyword(keyword);

        IPage<Role> roleIPage = roleService.list(roleGetListDto);

        return toJson(ResponseResult.success(roleIPage.getRecords(), roleIPage.getTotal()));
    }

    @ApiOperation(value="编辑角色", notes="编辑角色")
    @PutMapping("/{roleUid}")
    public String update(@PathVariable("roleUid") String roleUid, @RequestBody @Validated(value = {Update.class}) RoleEditDto roleEditDto) {
        roleEditDto.setUid(roleUid);
        Boolean res = roleService.updateById(roleEditDto);

        roleService.preheat();

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value="新增角色", notes="新增角色")
    @PostMapping("")
    public String add(@RequestBody @Validated(value = {Insert.class}) RoleEditDto roleEditDto) {
        Boolean res = roleService.save(roleEditDto);

        roleService.preheat();

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value="删除角色", notes="删除角色")
    @DeleteMapping("/{roleUid}")
    public String delete(@PathVariable("roleUid") String roleUid) {
        return toJson(roleService.removeById(roleUid));
    }

    @ApiOperation(value = "批量删除角色", notes = "批量删除角色")
    @DeleteMapping(value = "")
    public String delete(@RequestBody List<String> roleUidList) {
        return toJson(roleService.removeByIds(roleUidList));
    }

}
