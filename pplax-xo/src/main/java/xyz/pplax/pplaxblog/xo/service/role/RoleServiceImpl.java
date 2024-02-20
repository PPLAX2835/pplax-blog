package xyz.pplax.pplaxblog.xo.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.mapper.RoleMapper;

import java.io.Serializable;

/**
 * 角色权限表 服务实现类
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Role getById(Serializable id) {

        Role role = super.getById(id);

        // 检查role是否正常
        if (role == null || role.getStatus() != EStatus.ENABLE.getStatus()) {
            return null;
        }

        return role;
    }
}
