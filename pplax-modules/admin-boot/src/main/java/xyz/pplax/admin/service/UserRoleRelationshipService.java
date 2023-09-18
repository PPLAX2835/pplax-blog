package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.po.UserRoleRelationship;
import xyz.pplax.admin.pojo.UserRoleRelationshipPojo;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.data.entity.Condition;

import java.util.List;
import java.util.Objects;


@Service
public class UserRoleRelationshipService {
    @Autowired
    private PPLAXUserRoleService pplaxUserRoleService;

    public int deleteByUid(Long uid) {
        return pplaxUserRoleService.deleteById(uid);
    }

    public void insertUserRoleRelationship(UserRoleRelationshipPojo relationship) {
        relationshipIsFull(relationship);
        pplaxUserRoleService.insert(BeanUtils.copyProperties(relationship, UserRoleRelationship.class));
    }

    public List<UserRoleRelationship> selectAllUserRoleRelationship(Condition<Long> condition) {
        return pplaxUserRoleService.queryListByCondition(condition).getResult();
    }

    public int updateUserRoleRelationship(UserRoleRelationshipPojo relationship) {
        relationshipIsFull(relationship);
        Objects.requireNonNull(relationship.getUid(), "需要传入一个uid");
        return pplaxUserRoleService.updateById(BeanUtils.copyProperties(relationship, UserRoleRelationship.class));
    }

    private void relationshipIsFull(UserRoleRelationshipPojo relationship) {
        Objects.requireNonNull(relationship,"用户角色关系信息不能为null");
        Objects.requireNonNull(relationship.getRoleUid(),"用户角色的uid不能为null");
        Objects.requireNonNull(relationship.getUserUid(),"用户角色的用户uid不能为null");
    }
}
