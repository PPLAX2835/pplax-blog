package xyz.pplax.pplaxblog.admin.helper;

import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.utils.StrUtils;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.UserSO;
import xyz.pplax.pplaxblog.base.enums.EStatus;
import xyz.pplax.pplaxblog.base.helper.BaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 用户管理Helper
 */
public class UserHelper extends BaseHelper{
	
	static UserSO userSO; 
	
	private static UserSO getUserSO () {
		if (userSO == null) {
			userSO = getApplicationContext().getBean(UserSO.class);
		}
		return userSO;
	}
	
	/**
	 * 获取用户list
	 * @return
	 */
	public static Map<String, Object> getList(Map<String, Object> model) {
		Map<String, Object> map = getMap();
		map.put(SysConf.status, EStatus.ENABLE);
		List<User> list= getUserSO().getList(map);
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
		List<User> list= getUserSO().getList(map);
		List<User> newList = new ArrayList<User>();
		for(User user : list) {
			if(user.getStatus() != EStatus.DISABLED) {
				newList.add(user);
			}
		}
		model.put(SysConf.rows, newList);
		model.put(SysConf.total, newList.size());
		return model;
	}
}
