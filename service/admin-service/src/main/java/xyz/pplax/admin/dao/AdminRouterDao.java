package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.AdminRouter;
import xyz.pplax.service.base.BaseDao;

/**
 * admin_router 表DAO层
 */
@Mapper
public interface AdminRouterDao extends BaseDao<AdminRouter> {

}