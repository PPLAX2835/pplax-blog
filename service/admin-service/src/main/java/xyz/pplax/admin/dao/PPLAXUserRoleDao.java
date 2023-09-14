package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.UserRoleRelationship;
import xyz.pplax.service.base.BaseDao;

/**
 * user_role 表DAO层
 */
@Mapper
public interface PPLAXUserRoleDao extends BaseDao<UserRoleRelationship> {

}