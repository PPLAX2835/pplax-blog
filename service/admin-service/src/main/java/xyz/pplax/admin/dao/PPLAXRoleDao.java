package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.Role;
import xyz.pplax.service.base.BaseDao;

/**
 * role 数据表DAO层
 */
@Mapper
public interface PPLAXRoleDao extends BaseDao<Role> {

}