package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.Navigation;
import xyz.pplax.service.base.BaseDao;

/**
 * navigation 数据表DAO层
 */
@Mapper
public interface PPLAXNavigationDao extends BaseDao<Navigation> {

}