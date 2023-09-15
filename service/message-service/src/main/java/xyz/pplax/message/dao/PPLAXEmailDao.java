package xyz.pplax.message.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.message.po.Email;
import xyz.pplax.service.base.BaseDao;

/**
 * email 表DAO层
 */
@Mapper
public interface PPLAXEmailDao extends BaseDao<Email> {

}