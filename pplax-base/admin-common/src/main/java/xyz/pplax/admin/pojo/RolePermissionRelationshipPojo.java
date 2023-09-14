package xyz.pplax.admin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.pplax.core.valid.Delete;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * role_permission数据表的POJO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionRelationshipPojo {

    /**
     * 唯一uid，如果记录不多的话，可以直接使用int类型
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 角色的uid
     */
    @NotNull(groups = {Insert.class})
    private List<Long> roleUidArr;

    @NotNull(groups = {Insert.class})
    private List<Long> userUidArr;
    /**
     *
     */
    @NotNull(groups = {Insert.class})
    private List<Long> permissionUidArr;

    private List<Long> originRoleUidArr;
    private List<Long> newRoleUidArr;
    private List<Long> originPermissionUidArr;
    private List<Long> newPermissionUidArr;

    private List<String> usernameArr;
    private List<String> roleNameArr;
    private List<String> permissionPathArr;

}