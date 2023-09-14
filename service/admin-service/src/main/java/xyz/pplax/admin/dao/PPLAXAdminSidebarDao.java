package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.AdminSidebar;
import xyz.pplax.service.base.BaseDao;

/**
 * @table admin_sidebar <br/>
 * @description admin_sidebar 数据表DAO层 <br/>
 * @date 2022-12-13 22:06:00 <br/>
 * @author lax <br/>
 */

@Mapper
public interface PPLAXAdminSidebarDao extends BaseDao<AdminSidebar> {

}