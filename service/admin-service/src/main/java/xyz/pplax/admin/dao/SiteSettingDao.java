package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.SiteSetting;
import xyz.pplax.service.base.BaseDao;

/**
 * site_setting 表DAO层
 */
@Mapper
public interface SiteSettingDao extends BaseDao<SiteSetting> {

}