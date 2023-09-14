package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.WhiteUrl;
import xyz.pplax.service.base.BaseDao;

/**
 * white_url 表DAO层
 */
@Mapper
public interface PPLAXWhiteUrlDao extends BaseDao<WhiteUrl> {

}