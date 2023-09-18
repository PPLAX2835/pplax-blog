package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xyz.pplax.admin.po.Role;
import xyz.pplax.admin.pojo.RolePojo;
import xyz.pplax.admin.vo.RoleVO;
import xyz.pplax.core.constant.RedisConstant;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.role.RoleException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;
import xyz.pplax.service.redis.annotation.CleanRedisData;
import xyz.pplax.service.redis.annotation.GetByRedis;


@Service
public class RoleService {

    @Autowired
    private PPLAXRoleService pplaxRoleService;

    @Transactional
    @CleanRedisData
    public void insertRole(RolePojo role) {
        Assert.notNull(role, "角色信息不能为null");
        AssertUtils.ifNoLengthThrow(role.getName(), () -> new RoleException("角色名字不能为null"));
        // 查看此角色是否存在
        AssertUtils.stateThrow(queryListRoleByCondition(Condition.instant(role.getName())).getResult().isEmpty(),
                () -> new RoleException(ResponseStatusCodeEnum.PERMISSION_ROLE_HAD_EXISTS));
        role.setStatus(true);
        pplaxRoleService.insert(BeanUtils.copyProperties(role, Role.class));
    }

    @CleanRedisData(otherKey = RedisConstant.STORAGE_ROLE_PERMISSION_INFO)
    public int updateRole(RolePojo role) {
        // 判断角色是否存在
        Role queryRole = pplaxRoleService.queryById(role.getUid());
        if (queryRole != null && StringUtils.hasLength(role.getName()) && !queryRole.getName().equals(role.getName())) {
            Role one = pplaxRoleService.queryOne(new Role() {{
                setName(role.getName());
            }});
            AssertUtils.stateThrow(one == null, () -> new RoleException(ResponseStatusCodeEnum.PERMISSION_ROLE_HAD_EXISTS));
        }
        return pplaxRoleService.updateById(BeanUtils.copyProperties(role, Role.class));
    }

    @CleanRedisData(otherKey = RedisConstant.STORAGE_ROLE_PERMISSION_INFO)
    public int physicalDeleteRole(long uid) {
        return pplaxRoleService.deleteById(uid);
    }

    @GetByRedis
    public PageData<RoleVO> queryListRoleByCondition(Condition<Long> condition) {
        return PageUtils.copyPageDataResult(pplaxRoleService.queryListByCondition(condition), RoleVO.class);
    }

    @GetByRedis
    public RoleVO queryRoleByUid(long uid) {
        return BeanUtils.copyProperties(pplaxRoleService.queryById(uid), RoleVO.class);
    }

    @GetByRedis
    public RoleVO queryOneRole(RolePojo pojo) {
        return BeanUtils.copyProperties(pplaxRoleService.queryOne(BeanUtils.copyProperties(pojo, Role.class)), RoleVO.class);
    }
}
