package xyz.pplax.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.auth.po.LoginInfo;
import xyz.pplax.service.base.BaseDao;


@Mapper
public interface PPLAXLoginInfoDao extends BaseDao<LoginInfo> {

}