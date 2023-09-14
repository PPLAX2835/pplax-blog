package xyz.pplax.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.admin.po.Setting;
import xyz.pplax.service.base.BaseDao;

/**
 * setting 表DAO层
 */
@Mapper
public interface PPLAXSettingDao extends BaseDao<Setting> {

}