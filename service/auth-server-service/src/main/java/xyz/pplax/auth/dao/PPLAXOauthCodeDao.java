package xyz.pplax.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.auth.po.OauthCode;
import xyz.pplax.service.base.BaseDao;

/**
 * oauth_code表DAO层
 */
@Mapper
public interface PPLAXOauthCodeDao extends BaseDao<OauthCode> {

}