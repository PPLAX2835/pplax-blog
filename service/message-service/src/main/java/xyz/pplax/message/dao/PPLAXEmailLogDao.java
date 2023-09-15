package xyz.pplax.message.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.message.po.EmailLog;
import xyz.pplax.service.base.BaseDao;

/**
 * email_log 表DAO层
 */
@Mapper
public interface PPLAXEmailLogDao extends BaseDao<EmailLog> {

}