package xyz.pplax.article.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.article.po.Article;
import xyz.pplax.service.base.BaseDao;

/**
 * article 表DAO层
 */
@Mapper
public interface PPLAXArticleDao extends BaseDao<Article> {

}