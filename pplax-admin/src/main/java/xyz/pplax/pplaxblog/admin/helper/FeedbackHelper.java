package xyz.pplax.pplaxblog.admin.helper;

import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.utils.StrUtils;
import xyz.pplax.pplaxblog.xo.entity.Feedback;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.FeedbackSO;
import xyz.pplax.pplaxblog.xo.service.UserSO;
import xyz.pplax.pplaxblog.base.enums.EStatus;
import xyz.pplax.pplaxblog.base.helper.BaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 反馈管理Helper
 */
public class FeedbackHelper extends BaseHelper{
	
	static FeedbackSO feedbackSO;
	
	static UserSO userSO;
	
	private static FeedbackSO getFeedbackSO () {
		if (feedbackSO == null) {
			feedbackSO = getApplicationContext().getBean(FeedbackSO.class);
		}
		return feedbackSO;
	}
	
	private static UserSO getUserSO () {
		if (userSO == null) {
			userSO = getApplicationContext().getBean(UserSO.class);
		}
		return userSO;
	}
	
	/**
	 * 获取反馈list
	 * @return
	 */
	public static Map<String, Object> getList(Map<String, Object> model) {
		User user = null;
		Map<String, Object> map = getMap();
		map.put(SysConf.status, EStatus.ENABLE);
		List<Feedback> list= getFeedbackSO().getList(map);
		List<Feedback> newlist = new ArrayList<Feedback>();
		for(Feedback feedback : list) {
			if(feedback.getStatus() != EStatus.DISABLED) {
				String uid = feedback.getUseruid();
				user = getUserSO().get(uid);
				if(user != null) {
					feedback.setUsername(user.getUsername());
				}
				newlist.add(feedback);
			}
		}
		model.put(SysConf.rows, newlist);
		model.put(SysConf.total, newlist.size());
		return model;
	}
	
	/**
	 * 搜索反馈list
	 * @author xuzhixiang
	 * @date 2017年12月28日17:42:57
	 * @return
	 */
	public static Map<String, Object> getSearchList(Map<String, Object> model, String keyword) {
		User user = null;
		Map<String, Object> map = getMap();
		if(StrUtils.isNotEmpty(keyword)) {
			map.put(SysConf.keyword, keyword);
		}
		List<Feedback> list= getFeedbackSO().getList(map);
		List<Feedback> newlist = new ArrayList<Feedback>();
		for(Feedback feedback : list) {
			if(feedback.getStatus() != EStatus.DISABLED) {
				String uid = feedback.getUseruid();
				user = getUserSO().get(uid);
				if(user != null) {
					feedback.setUsername(user.getUsername());
				}
				newlist.add(feedback);
			}

		}
		model.put(SysConf.rows, newlist);
		model.put(SysConf.total, newlist.size());
		return model;
	}
}
