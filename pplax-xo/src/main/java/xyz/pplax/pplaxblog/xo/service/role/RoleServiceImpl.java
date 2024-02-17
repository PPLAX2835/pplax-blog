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
    private RoleMapper roleMapper;

    @Autowired
    private PathAccessPermissionMapper pathAccessPermissionMapper;

    @Override
    public Role getById(Serializable id) {
        // 先从缓存中找
        Role role = JSONObject.toJavaObject(redisService.getCacheObject(RoleRedisConstants.ROLE + BaseRedisConstants.SEGMENTATION + id), Role.class);

        if (role == null) {
            // 缓存中没有
            role = roleMapper.selectById(id);

            // 检查role是否正常
            if (role == null || role.getStatus() != EStatus.ENABLE.getStatus()) {
                return null;
            }

            String[] pathAccessPermissionUidArray = role.getPathAccessPermissionUid().split(",");
            List<PathAccessPermission> pathAccessPermissionUidList = new ArrayList<>();

            // 添加该角色的权限
            for (String pathAccessPermissionUid : pathAccessPermissionUidArray) {
                pathAccessPermissionUidList.add(pathAccessPermissionMapper.selectById(pathAccessPermissionUid));
            }
            role.setPathAccessPermissionList(pathAccessPermissionUidList);

            // 存到缓存中
            redisService.setCacheObject(RoleRedisConstants.ROLE + BaseRedisConstants.SEGMENTATION + id, role);
        }
        return role;
    }
}
