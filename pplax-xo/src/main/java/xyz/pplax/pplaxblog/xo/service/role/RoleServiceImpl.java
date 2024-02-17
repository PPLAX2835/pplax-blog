package xyz.pplax.pplaxblog.xo.service.role;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.starter.redis.constants.BaseRedisConstants;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.constants.redis.RoleRedisConstants;
import xyz.pplax.pplaxblog.xo.entity.PathAccessPermission;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.mapper.PathAccessPermissionMapper;
import xyz.pplax.pplaxblog.xo.mapper.RoleMapper;
import xyz.pplax.pplaxblog.xo.service.pathaccesspermission.PathAccessPermissionService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限表 服务实现类
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private PathAccessPermissionService pathAccessPermissionService;

    @Override
    public Role getById(Serializable id) {

        Role role = super.getById(id);

        // 检查role是否正常
        if (role == null || role.getStatus() != EStatus.ENABLE.getStatus()) {
            return null;
        }

        List<PathAccessPermission> pathAccessPermissionList = role.getPathAccessPermissionList();
        if (pathAccessPermissionList == null || pathAccessPermissionList.size() == 0) {
            // 如果角色中没有封装权限，则添加该角色的权限
            String[] pathAccessPermissionUidArray = role.getPathAccessPermissionUid().split(",");
            List<PathAccessPermission> pathAccessPermissionUidList = new ArrayList<>();
            for (String pathAccessPermissionUid : pathAccessPermissionUidArray) {
                pathAccessPermissionUidList.add(pathAccessPermissionService.getById(pathAccessPermissionUid));
            }
            role.setPathAccessPermissionList(pathAccessPermissionUidList);
        }

        return role;
    }
}
