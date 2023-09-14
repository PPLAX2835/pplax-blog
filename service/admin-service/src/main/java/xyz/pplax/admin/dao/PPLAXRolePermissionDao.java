package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.RolePermissionRelationship;
import xyz.pplax.service.base.BaseDao;

/**
 * role_permission表DAO层
 */
@Mapper
public interface PPLAXRolePermissionDao extends BaseDao<RolePermissionRelationship> {

}