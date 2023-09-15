package xyz.pplax.article.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.article.po.Talk;
import xyz.pplax.service.base.BaseDao;

/**
 * talk 数据表DAO层
 */
@Mapper
public interface PPLAXTalkDao extends BaseDao<Talk> {

}