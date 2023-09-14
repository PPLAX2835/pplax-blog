package xyz.pplax.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.auth.po.LoginInfo;
import xyz.pplax.service.base.BaseDao;

/**
 * 登录信息 dao
 */
@Mapper
public interface PPLAXLoginInfoDao extends BaseDao<LoginInfo> {

}