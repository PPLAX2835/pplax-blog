  package xyz.pplax.admin.controller;

  import io.swagger.v3.oas.annotations.Operation;
  import io.swagger.v3.oas.annotations.tags.Tag;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.validation.annotation.Validated;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestBody;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RestController;
  import xyz.pplax.admin.po.Permission;
  import xyz.pplax.admin.pojo.PermissionPojo;
  import xyz.pplax.admin.service.PermissionService;
  import xyz.pplax.core.annotaion.controller.ModifyOperation;
  import xyz.pplax.core.annotaion.controller.SelectOperation;
  import xyz.pplax.core.valid.Insert;
  import xyz.pplax.core.valid.Update;
  import xyz.pplax.data.entity.Condition;
  import xyz.pplax.data.entity.PageData;

  import javax.validation.groups.Default;


@RequestMapping("/admin/permission")
@RestController
@Tag(name = "权限相关的操作")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ModifyOperation
    @Operation(summary = "插入路径权限")
    @PostMapping("/insertPermission")
    public void insertPermission(@Validated({Insert.class, Default.class}) @RequestBody PermissionPojo permission) {
        permissionService.insertPermission(permission);
    }

    @ModifyOperation
    @Operation(summary = "批量插入路径权限")
    @PostMapping("/batchInsertPermission")
    public void batchInsertPermission(@Validated({Insert.class, Default.class}) @RequestBody PermissionPojo permission) {
        permissionService.batchInsertPermission(permission);
    }

    @ModifyOperation
    @Operation(summary = "修改路径权限信息")
    @PostMapping("/updatePermission")
    public int updatePermission(@Validated({Update.class, Default.class}) @RequestBody PermissionPojo permission) {
        return permissionService.updatePermission(permission);
    }

    @ModifyOperation
    @Operation(summary = "删除权限")
    @PostMapping("/physicalDeletePermission")
    public int physicalDeletePermission(@RequestBody PermissionPojo permission) {
        return permissionService.physicalDeletePermission(permission.getUid());
    }

    @ModifyOperation
    @Operation(summary = "删除权限")
    @PostMapping("/batchPhysicalDeletePermission")
    public int batchPhysicalDeletePermission(@RequestBody PermissionPojo permission) {
        return permissionService.batchPhysicalDeletePermission(permission);
    }

    @SelectOperation
    @Operation(summary = "根据uid查询权限")
    @PostMapping("/queryPermissionByUid")
    public Permission queryPermissionByUid(@RequestBody PermissionPojo permission) {
        return permissionService.queryPermissionByUid(permission.getUid());
    }

    @SelectOperation
    @Operation(summary = "查询满足要求的所有权限信息")
    @PostMapping("/queryListPermissionByCondition")
    public PageData<Permission> queryListPermissionByCondition(@RequestBody Condition<Long> condition) {
        return permissionService.queryListPermissionByCondition(condition);
    }
}
