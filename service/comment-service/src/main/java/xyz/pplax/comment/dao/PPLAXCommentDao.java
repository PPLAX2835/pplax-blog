package xyz.pplax.comment.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.comment.po.Comment;
import xyz.pplax.service.base.BaseDao;

/**
 * comment 表DAO层
 */
@Mapper
public interface PPLAXCommentDao extends BaseDao<Comment> {

}