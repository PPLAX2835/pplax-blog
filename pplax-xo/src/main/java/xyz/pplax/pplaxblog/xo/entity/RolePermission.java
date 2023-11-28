package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.base.entity.SuperEntity;

/**
 * @description 角色权限表
 * @author PPLAX
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_role_permission")
public class RolePermission extends SuperEntity<RolePermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private String roleUid;

    /**
     * 权限id
     */
    private String permissionUid;

    public RolePermission() {}
}