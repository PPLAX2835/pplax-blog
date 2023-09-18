package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.po.RolePermissionRelationship;
import xyz.pplax.admin.pojo.RolePermissionRelationshipPojo;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.data.entity.Condition;

import java.util.List;
import java.util.Objects;


@Service
public class RolePermissionRelationshipService {

    @Autowired
    private PPLAXRolePermissionService pplaxRolePermissionService;

    public int deleteByUid(Long uid) {
        Objects.requireNonNull(uid, "需要传入uid");
        return pplaxRolePermissionService.deleteById(uid);
    }

    public void insertRolePermissionRelationship(RolePermissionRelationshipPojo relationship) {
        relationshipIsFull(relationship);
        pplaxRolePermissionService.insert(BeanUtils.copyProperties(relationship, RolePermissionRelationship.class));
    }

    public int updateRolePermissionRelationship(RolePermissionRelationshipPojo relationship) {
        relationshipIsFull(relationship);
        Objects.requireNonNull(relationship.getUid(), "需要传入uid");
        return pplaxRolePermissionService.updateById(BeanUtils.copyProperties(relationship, RolePermissionRelationship.class));
    }

    public List<RolePermissionRelationship> selectAllRolePermissionRelationship(Condition<Long> condition) {
        return pplaxRolePermissionService.queryListByCondition(condition).getResult();
    }

    private void relationshipIsFull(RolePermissionRelationshipPojo relationship) {
        Objects.requireNonNull(relationship,"用户角色关系信息不能为null");
        Objects.requireNonNull(relationship.getRoleUidArr(),"用户角色的uid不能为null");
        Objects.requireNonNull(relationship.getPermissionUidArr(),"用户角色的用户uid不能为null");
    }
}
