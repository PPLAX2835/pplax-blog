package xyz.pplax.message.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.message.po.MessageLog;
import xyz.pplax.service.base.BaseDao;

/**
 * message_log 数据表DAO层
 */
@Mapper
public interface PPLAXMessageLogDao extends BaseDao<MessageLog> {

}