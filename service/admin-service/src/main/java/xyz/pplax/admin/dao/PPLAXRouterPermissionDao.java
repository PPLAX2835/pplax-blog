package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.RouterPermission;
import xyz.pplax.service.base.BaseDao;

/**
 * au_router_permission 表DAO层
 */
@Mapper
public interface PPLAXRouterPermissionDao extends BaseDao<RouterPermission> {

}