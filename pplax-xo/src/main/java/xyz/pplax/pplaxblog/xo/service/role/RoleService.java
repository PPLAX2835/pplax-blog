package xyz.pplax.pplaxblog.xo.service.role;

import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.entity.Role;

/**
 * 角色 服务类
 */
public interface RoleService extends SuperService<Role> {

    Role setMenu(Role role);

}
