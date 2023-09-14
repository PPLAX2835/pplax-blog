package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.Social;
import xyz.pplax.service.base.BaseDao;

/**
 * social 数据表DAO层
 */
@Mapper
public interface PPLAXSocialDao extends BaseDao<Social> {

}