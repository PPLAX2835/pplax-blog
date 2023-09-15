package xyz.pplax.article.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.article.po.Category;
import xyz.pplax.service.base.BaseDao;

/**
 * category 表DAO层
 */
@Mapper
public interface PPLAXCategoryDao extends BaseDao<Category> {

}