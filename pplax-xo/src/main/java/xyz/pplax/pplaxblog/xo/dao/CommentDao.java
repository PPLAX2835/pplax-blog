package xyz.pplax.pplaxblog.xo.dao;

import org.springframework.stereotype.Repository;
import xyz.pplax.pplaxblog.base.dao.BaseDao;
import xyz.pplax.pplaxblog.xo.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * 评论Dao
 */
@Repository
public interface CommentDao extends BaseDao<Comment> {
	/**
	 * ͨ通过map获取一级评论
	 * @param map
	 * @return
	 */
	public List<Comment> getFirstList(Map<String, Object> map);
}
