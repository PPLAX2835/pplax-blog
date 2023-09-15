package xyz.pplax.message.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.message.po.MailTemplate;
import xyz.pplax.service.base.BaseDao;

/**
 * mail_template 数据表DAO层
 */
@Mapper
public interface PPLAXMailTemplateDao extends BaseDao<MailTemplate> {

}