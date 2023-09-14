package xyz.pplax.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.auth.po.OauthClientDetails;
import xyz.pplax.service.base.BaseDao;

/**
 * oauth_client_details表DAO层
 */
@Mapper
public interface PPLAXOauthClientDetailsDao extends BaseDao<OauthClientDetails> {

}