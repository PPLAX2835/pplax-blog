package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xyz.pplax.admin.dao.ext.PermissionRelationDaoExt;
import xyz.pplax.admin.dto.RolePermissionDTO;
import xyz.pplax.admin.po.Permission;
import xyz.pplax.admin.po.Role;
import xyz.pplax.admin.po.RolePermissionRelationship;
import xyz.pplax.admin.po.UserRoleRelationship;
import xyz.pplax.admin.pojo.RolePermissionRelationshipPojo;
import xyz.pplax.admin.vo.UserVO;
import xyz.pplax.core.constant.RedisConstant;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.permission.PermissionException;
import xyz.pplax.core.exception.role.RoleException;
import xyz.pplax.core.exception.user.UserException;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.service.redis.annotation.CleanRedisData;
import xyz.pplax.service.redis.annotation.GetByRedis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PermissionRelationService {

    private final String rolePrefix = "ROLE_";

    public static String aa = "";

    @Autowired
    private PPLAXUserRoleService pplaxUserRoleService;
    @Autowired
    private PPLAXRolePermissionService pplaxRolePermissionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionRelationDaoExt permissionRelationDao;

    public List<RolePermissionDTO> loadPermissionByUserUid(RolePermissionRelationshipPojo pojo) {
        Assert.notNull(pojo.getUserUidArr(), "userUidArr不能为null");
        return packageCollectResult(permissionRelationDao.loadPermissionByUserUid(pojo.getUserUidArr().get(0)));
    }

    public List<Role> loadAllRoleByUsername(RolePermissionRelationshipPojo pojo) {
        Assert.notNull(pojo.getUsernameArr(), "usernameArr不能为null");
        String username = pojo.getUsernameArr().get(0);
        UserVO userVO = userService.queryUserByUsername(username);
        AssertUtils.stateThrow(username != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        return permissionRelationDao.loadAllRoleByUserUid(userVO.getUid()).stream()
                .peek(role -> role.setName(rolePrefix + role.getName()))
                .collect(Collectors.toList());
    }

    public List<RolePermissionDTO> loadPermissionByUsername(RolePermissionRelationshipPojo pojo) {
        Assert.notNull(pojo.getUsernameArr(), "usernameArr不能为null");
        String username = pojo.getUsernameArr().get(0);
        UserVO userVO = userService.queryUserByUsername(username);
        if (userVO == null) {
            return new ArrayList<>();
        }
        return packageCollectResult(permissionRelationDao.loadPermissionByUserUid(userVO.getUid()));
    }

    public List<RolePermissionDTO> loadPermissionByRoleName(RolePermissionRelationshipPojo pojo) {
        Assert.notNull(pojo.getRoleNameArr(), "roleNameArr不能为null");
        return packageCollectResult(permissionRelationDao.loadPermissionByRoleName(pojo.getRoleNameArr().get(0)));
    }

    @GetByRedis(key = RedisConstant.STORAGE_ROLE_PERMISSION_INFO, expriedSecond = 60 * 60 * 24)
    public List<RolePermissionDTO> loadAllRolePermission(Condition<Long> condition) {
        return packageCollectResult(permissionRelationDao.loadAllRolePermission(condition));
    }

    public List<RolePermissionDTO> loadRolePermissionRelByRoleUid(RolePermissionRelationshipPojo pojo) {
        List<Long> roleUidArr = pojo.getRoleUidArr();
        List<RolePermissionDTO> rolePermissionList = new ArrayList<>();
        roleUidArr.forEach(v -> rolePermissionList.addAll(permissionRelationDao.loadRolePermissionRelByRoleUid(v)));
        return rolePermissionList;
    }

    public List<RolePermissionDTO> queryRoleByPermissionPath(RolePermissionRelationshipPojo pojo) {
        Assert.notNull(pojo.getPermissionPathArr(), "permissionPathArr不能为null");
        return packageCollectResult(permissionRelationDao.queryRoleByPermissionPath(pojo.getPermissionPathArr().get(0)));
    }

    @CleanRedisData(key = RedisConstant.STORAGE_ROLE_PERMISSION_INFO)
    @Transactional
    public void insertUserRoleBatch(RolePermissionRelationshipPojo pojo) {
        Assert.notNull(pojo.getUserUidArr(), "userUidArr不能为null");
        Assert.notNull(pojo.getRoleUidArr(), "roleUidArr不能为null");
        List<Long> userUidArr = pojo.getUserUidArr();
        List<Long> roleUidArr = pojo.getRoleUidArr();
        roleUidArr.forEach(roleUid -> {
            Role role = roleService.queryRoleByUid(roleUid);
            if (role == null) {
                return;
            }
            // 查询看是否存在此角色
            userUidArr.stream()
                    .filter(userUid -> userService.queryUserByUid(userUid) != null)
                    .filter(userUid -> {
                        UserRoleRelationship relationship = new UserRoleRelationship();
                        relationship.setRoleUid(roleUid);
                        relationship.setUserUid(userUid);
                        return pplaxUserRoleService.queryListByWhere(relationship).isEmpty();
                    })
                    .forEach(userUid -> {
                        UserRoleRelationship userRoleRelationship = new UserRoleRelationship();
                        userRoleRelationship.setRoleUid(roleUid);
                        userRoleRelationship.setUserUid(userUid);
                        pplaxUserRoleService.insert(userRoleRelationship);
                    });
        });
    }

    @CleanRedisData(key = RedisConstant.STORAGE_ROLE_PERMISSION_INFO)
    public int deleteUserRoleBatch(RolePermissionRelationshipPojo pojo) {
        Assert.notNull(pojo.getUserUidArr(), "userUidArr不能为null");
        Assert.notNull(pojo.getRoleUidArr(), "roleUidArr不能为null");
        Long userUid = pojo.getUserUidArr().get(0);
        List<Long> roleUidArr = pojo.getRoleUidArr();
        final int[] successNum = {0};
        // 判断用户是否存在
        AssertUtils.stateThrow(userService.queryUserByUid(userUid) != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));

        // 组装查询条件
        Condition<Long> condition = new Condition<>();
        condition.setUid(userUid);
        roleUidArr.stream().forEach(roleUid -> {
            condition.setOtherUid(roleUid);
            // 查询roleUid和userUid对应的uid
            List<UserRoleRelationship> userRoleRelationshipList = pplaxUserRoleService
                    .queryListByCondition(condition).getResult();
            if (userRoleRelationshipList.size() > 0) {
                successNum[0] = successNum[0] + pplaxUserRoleService.deleteById(userRoleRelationshipList.get(0).getUid());
            }
        });
        return successNum[0];
    }

    @CleanRedisData(key = RedisConstant.STORAGE_ROLE_PERMISSION_INFO)
    public int updateUserRole(RolePermissionRelationshipPojo pojo) {
        Assert.notNull(pojo.getUserUidArr(), "userUidArr不能为null");
        Assert.notNull(pojo.getOriginRoleUidArr(), "originRoleUidArr不能为null");
        Assert.notNull(pojo.getNewRoleUidArr(), "newRoleUidArr不能为null");
        Long userUid = pojo.getUserUidArr().get(0);
        Long originRoleUid = pojo.getOriginRoleUidArr().get(0);
        Long newRoleUid = pojo.getNewRoleUidArr().get(0);
        final int[] successNum = {0};
        // 判断用户是否存在
        AssertUtils.stateThrow(userService.queryUserByUid(userUid) != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        // 更新用户角色关系
        Condition<Long> condition = new Condition<>();
        condition.setUid(userUid);
        condition.setOtherUid(originRoleUid);

        List<UserRoleRelationship> userRoleRelationshipList = pplaxUserRoleService
                .queryListByCondition(condition).getResult();
        if (userRoleRelationshipList.size() > 0) {
            // 判断newRoleUid是否存在
            AssertUtils.stateThrow(roleService.queryRoleByUid(newRoleUid) != null,
                    () -> new RoleException(ResponseStatusCodeEnum.PERMISSION_ROLE_NOT_EXISTS));
            // 更新
            userRoleRelationshipList.get(0).setRoleUid(newRoleUid);
            successNum[0] = pplaxUserRoleService.updateById(userRoleRelationshipList.get(0));
        }

        return successNum[0];
    }

    @CleanRedisData(key = RedisConstant.STORAGE_ROLE_PERMISSION_INFO)
    public void insertRolePermissionBatch(RolePermissionRelationshipPojo pojo) {
        Assert.notNull(pojo.getPermissionUidArr(), "permissionUidArr不能为null");
        Assert.notNull(pojo.getRoleUidArr(), "roleUidArr不能为null");
        List<Long> roleUidArr = pojo.getRoleUidArr();
        List<Long> permissionUidArr = pojo.getPermissionUidArr();

        // 判断此permissionUid是否可用
        permissionUidArr.forEach(permissionUid -> {
            Permission permission = permissionService.queryPermissionByUid(permissionUid);
            if (permission == null) {
                return;
            }
            roleUidArr.stream()
                    .filter(roleUid -> roleService.queryRoleByUid(roleUid) != null)
                    .filter(roleUid -> pplaxRolePermissionService.queryListByCondition(Condition.instant(roleUid, permissionUid)).getResult().isEmpty())
                    .forEach(roleUid -> {
                        // 创建角色路径关系
                        RolePermissionRelationship rolePermissionRelationship = RolePermissionRelationship.builder()
                                .permissionUid(permissionUid)
                                .roleUid(roleUid)
                                .build();
                        // 插入角色路径关系
                        pplaxRolePermissionService.insert(rolePermissionRelationship);
                    });
        });
    }

    @CleanRedisData(key = RedisConstant.STORAGE_ROLE_PERMISSION_INFO)
    public int deleteRolePermissionBatch(RolePermissionRelationshipPojo pojo) {
        Assert.notNull(pojo.getPermissionUidArr(), "permissionUidArr不能为null");
        Assert.notNull(pojo.getRoleUidArr(), "roleUidArr不能为null");
        Long roleUid = pojo.getRoleUidArr().get(0);
        List<Long> permissionUidArr = pojo.getPermissionUidArr();
        final int[] successNum = {0};
        // 判断角色是否存在
        AssertUtils.stateThrow(roleService.queryRoleByUid(roleUid) != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_ROLE_NOT_EXISTS));
        // 组装查询条件
        Condition<Long> condition = new Condition<>();
        condition.setUid(roleUid);
        permissionUidArr.stream().forEach(permissionUid -> {
            // 查询出roleUid和permissionUid对应的uid
            condition.setOtherUid(permissionUid);
            List<RolePermissionRelationship> rolePermissionRelationshipList = pplaxRolePermissionService.queryListByCondition(condition).getResult();
            if (rolePermissionRelationshipList.size() > 0) {
                // 删除
                successNum[0] = successNum[0] + pplaxRolePermissionService.deleteById(rolePermissionRelationshipList.get(0).getUid());
            }
        });

        return successNum[0];
    }

    @CleanRedisData(key = RedisConstant.STORAGE_ROLE_PERMISSION_INFO)
    public int updateRolePermission(RolePermissionRelationshipPojo pojo) {
        Assert.notNull(pojo.getOriginPermissionUidArr(), "originPermissionUidArr不能为null");
        Assert.notNull(pojo.getRoleUidArr(), "roleUidArr不能为null");
        Assert.notNull(pojo.getNewPermissionUidArr(), "newPermissionUidArr不能为null");
        Long roleUid = pojo.getRoleUidArr().get(0);
        Long originPermissionUid = pojo.getOriginPermissionUidArr().get(0);
        Long newPermissionUid = pojo.getNewPermissionUidArr().get(0);
        final int[] successNum = {0};
        // 判断角色是否存在
        AssertUtils.stateThrow(roleService.queryRoleByUid(roleUid) != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_ROLE_NOT_EXISTS));
        // 更新用户角色关系
        Condition<Long> condition = new Condition<>();
        condition.setUid(roleUid);
        condition.setOtherUid(originPermissionUid);
        List<RolePermissionRelationship> permissionRelationshipList = pplaxRolePermissionService.queryListByCondition(condition).getResult();
        if (permissionRelationshipList.size() > 0) {
            // 判断此newPermissionUid是否存在
            AssertUtils.stateThrow(permissionService.queryPermissionByUid(newPermissionUid) != null,
                    () -> new PermissionException(ResponseStatusCodeEnum.PERMISSION_RESOURCE_NOT_RIGHT));
            // 更新
            permissionRelationshipList.get(0).setPermissionUid(newPermissionUid);
            successNum[0] = pplaxRolePermissionService.updateById(permissionRelationshipList.get(0));
        }
        return successNum[0];
    }

    /**
     * 因为存放在数据库中的角色名没有加上前缀ROLE_，所以统一在这里，对返回结果的角色名，加上前缀
     * @param rolePermissionDTOList
     * @return
     */
    private List<RolePermissionDTO> packageCollectResult(List<RolePermissionDTO> rolePermissionDTOList) {
        return rolePermissionDTOList.stream()
                .peek(rolePermissionDTO -> rolePermissionDTO.setRoleName(rolePrefix + rolePermissionDTO.getRoleName()))
                .collect(Collectors.toList());
    }
}
