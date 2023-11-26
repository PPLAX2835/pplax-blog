package xyz.pplax.pplaxblog.admin.helper;

import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.utils.StrUtils;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.BlogSO;
import xyz.pplax.pplaxblog.xo.service.CommentSO;
import xyz.pplax.pplaxblog.xo.service.UserSO;
import xyz.pplax.pplaxblog.base.enums.EStatus;
import xyz.pplax.pplaxblog.base.helper.BaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 评论管理Helper
 */
public class CommentHelper extends BaseHelper{
	
	static CommentSO commentSO;
	
	static UserSO userSO;
	
	static BlogSO blogSO;
	
	private static CommentSO getCommentSO () {
		if (commentSO == null) {
			commentSO = getApplicationContext().getBean(CommentSO.class);
		}
		return commentSO;
	}
	
	private static UserSO getUserSO() {
		if (userSO == null) {
			userSO = getApplicationContext().getBean(UserSO.class);
		}
		return userSO;
	}
	
	private static BlogSO getBlogSO() {
		if (blogSO == null) {
			blogSO = getApplicationContext().getBean(BlogSO.class);
		}
		return blogSO;
	}
	
	/**
	 * 获取用户list
	 * @return
	 */
	public static Map<String, Object> getList(Map<String, Object> model) {
		Map<String, Object> map = getMap();
		map.put(SysConf.status, EStatus.ENABLE);
		List<Comment> list= getCommentSO().getList(map);
		model.put(SysConf.rows, list);
		model.put(SysConf.total, list.size());
		return model;
	}
	
	/**
	 * 搜索用户list
	 * @author xuzhixiang
	 * @date 2017年12月29日14:32:13
	 * @return
	 */
	public static Map<String, Object> getSearchList(Map<String, Object> model, String keyword) {
		Map<String, Object> map = getMap();
		if(StrUtils.isNotEmpty(keyword)) {
			map.put(SysConf.keyword, keyword);
		}	
		List<Comment> list= getCommentSO().getList(map);
		List<Comment> newList = new ArrayList<Comment>();
		for(Comment comment : list) {
			if(comment.getStatus() != EStatus.DISABLED) {
				Blog blog = getBlogSO().get(comment.getBloguid());
				if(blog != null) {
					comment.setBlogtitle(blog.getTitle());
				}
				//获取发送的用户名
				User touser = getUserSO().get(comment.getTouseruid());
				if(touser != null) {
					comment.setTousername(touser.getUsername());
				}
				//获取发送消息的用户名
				User user = getUserSO().get(comment.getUseruid());
				if(user != null) {
					comment.setUsername(user.getUsername());
				}
				newList.add(comment);
			}
		}
		model.put(SysConf.rows, newList);
		model.put(SysConf.total, newList.size());
		return model;
	}
}
