package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.Permission;
import xyz.pplax.service.base.BaseDao;

/**
 * permission 数据表DAO层
 */
@Mapper
public interface PPLAXPermissionDao extends BaseDao<Permission> {

}