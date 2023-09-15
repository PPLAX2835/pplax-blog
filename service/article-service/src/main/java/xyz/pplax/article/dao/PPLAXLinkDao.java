package xyz.pplax.article.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.article.po.Link;
import xyz.pplax.service.base.BaseDao;

/**
 * link 表DAO层
 */
@Mapper
public interface PPLAXLinkDao extends BaseDao<Link> {

}