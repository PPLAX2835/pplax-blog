package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.Site;
import xyz.pplax.service.base.BaseDao;

/**
 * site 表DAO层
 */
@Mapper
public interface PPLAXSiteDao extends BaseDao<Site> {

}