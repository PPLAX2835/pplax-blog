package xyz.pplax.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 角色权限
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDTO {
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色的状态
     */
    private Boolean roleStatus;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 此角色可以允许访问的路径
     */
    private String path;

    /**
     * 用户的userUid
     */
    private Long userUid;
}
