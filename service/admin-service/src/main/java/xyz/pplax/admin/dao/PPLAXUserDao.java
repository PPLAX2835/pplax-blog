package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.User;
import xyz.pplax.service.base.BaseDao;

/**
 * user 表DAO层
 */
@Mapper
public interface PPLAXUserDao extends BaseDao<User> {

}