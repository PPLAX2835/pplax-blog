package xyz.pplax.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.comment.dao.PPLAXCommentDao;
import xyz.pplax.comment.po.Comment;
import xyz.pplax.service.base.BaseService;

/**
 * comment 表Service层
 */
@Service
public class PPLAXCommentService extends BaseService<Comment> {
	@SuppressWarnings("unused")
	private PPLAXCommentDao commentDao;
	
	@Autowired
    public void setInfoDao(PPLAXCommentDao commentDao) {
        super.setBaseDao(commentDao);
        this.commentDao = commentDao;
    }
}