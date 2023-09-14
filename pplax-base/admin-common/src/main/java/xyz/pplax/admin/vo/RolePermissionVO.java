package xyz.pplax.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.admin.po.RolePermissionRelationship;

/**
 * role_permission数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "role_permission数据表的VO")
public class RolePermissionVO extends RolePermissionRelationship {

}