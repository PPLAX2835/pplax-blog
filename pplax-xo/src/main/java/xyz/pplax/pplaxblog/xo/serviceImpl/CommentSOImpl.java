package xyz.pplax.pplaxblog.xo.serviceImpl;

import xyz.pplax.pplaxblog.xo.dao.CommentDao;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.service.CommentSO;
import xyz.pplax.pplaxblog.base.serviceImpl.BaseSOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 评论Service实现
 */
@Service
public class CommentSOImpl extends BaseSOImpl<Comment> implements CommentSO{
	
	@Autowired
	CommentDao dao;
	
	public List<Comment> getFirstList(Map<String, Object> map) {
		return dao.getFirstList(map);
	}

}
