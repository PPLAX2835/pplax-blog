package xyz.pplax.pplaxblog.xo.service;

import xyz.pplax.pplaxblog.base.service.BaseSO;
import xyz.pplax.pplaxblog.xo.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * 评论SO
 */
public interface CommentSO extends BaseSO<Comment> {
	/**
	 * 获取一级评论
	 * @param map
	 * @return
	 */
	public List<Comment> getFirstList(Map<String, Object> map);
}
