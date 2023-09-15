package xyz.pplax.oauth.api.service.handler;

import org.springframework.stereotype.Component;
import xyz.pplax.admin.pojo.RolePermissionRelationshipPojo;
import xyz.pplax.core.entity.R;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.oauth.api.service.RolePermissionFeignService;

/**
 * 角色权限信息控制
 */
@Component
public class RolePermissionFeignHandler implements RolePermissionFeignService {


    @Override
    public R loadPermissionByUsername(RolePermissionRelationshipPojo pojo) {
        return R.failure();
    }

    @Override
    public R loadAllRoleByUsername(RolePermissionRelationshipPojo pojo) {
        return R.failure();
    }

    @Override
    public R loadAllRolePermission(Condition<Long> condition) {
        return R.failure();
    }
}
