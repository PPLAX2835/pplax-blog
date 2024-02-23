package xyz.pplax.pplaxblog.xo.service.user;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.mapper.UserMapper;
import xyz.pplax.pplaxblog.xo.service.role.RoleService;

/**
 * 用户表 服务实现类
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private RoleService roleService;

    /**
     * 获取用户的角色，包含菜单
     * @param userUid
     * @return
     */
    @Override
    public Role getRoleWithMenu(String userUid) {
        User user = getById(userUid);
        if (user == null) {
            log.warn("没有获取到用户信息");
            return null;
        }
        Role role = roleService.getById(user.getRoleUid());
        return roleService.setMenu(role);
    }
}
