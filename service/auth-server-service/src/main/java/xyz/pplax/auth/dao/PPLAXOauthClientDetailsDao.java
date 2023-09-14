package xyz.pplax.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.auth.po.OauthClientDetails;
import xyz.pplax.service.base.BaseDao;

/**
 * 数据表DAO层
 */
@Mapper
public interface PPLAXOauthClientDetailsDao extends BaseDao<OauthClientDetails> {

}