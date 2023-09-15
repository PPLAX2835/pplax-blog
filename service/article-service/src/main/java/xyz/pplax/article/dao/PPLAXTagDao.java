package xyz.pplax.article.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.article.po.Tag;
import xyz.pplax.service.base.BaseDao;

/**
 * tag 表DAO层
 */
@Mapper
public interface PPLAXTagDao extends BaseDao<Tag> {

}