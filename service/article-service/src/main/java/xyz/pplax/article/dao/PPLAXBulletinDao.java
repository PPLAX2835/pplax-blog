package xyz.pplax.article.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.article.po.Bulletin;
import xyz.pplax.service.base.BaseDao;

/**
 * bulletin 表DAO层
 */
@Mapper
public interface PPLAXBulletinDao extends BaseDao<Bulletin> {

}