package xyz.pplax.pplaxblog.admin.helper;

import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.utils.StrUtils;
import xyz.pplax.pplaxblog.xo.entity.Visitor;
import xyz.pplax.pplaxblog.xo.service.VisitorSO;
import xyz.pplax.pplaxblog.base.enums.EStatus;
import xyz.pplax.pplaxblog.base.helper.BaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 游客管理Helper
 */
public class VisitorHelper extends BaseHelper{
	
	static VisitorSO visitorSO; 
	
	private static VisitorSO getVisitorSO () {
		if (visitorSO == null) {
			visitorSO = getApplicationContext().getBean(VisitorSO.class);
		}
		return visitorSO;
	}
	
	/**
	 * 获取用户list
	 * @return
	 */
	public static Map<String, Object> getList(Map<String, Object> model) {
		Map<String, Object> map = getMap();
		map.put(SysConf.status, EStatus.ENABLE);
		List<Visitor> list= getVisitorSO().getList(map);
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
		List<Visitor> list= getVisitorSO().getList(map);
		List<Visitor> newList = new ArrayList<Visitor>();
		for(Visitor visitor : list) {
			if(visitor.getStatus() != EStatus.DISABLED) {
				newList.add(visitor);
			}
		}
		model.put(SysConf.rows, newList);
		model.put(SysConf.total, newList.size());
		return model;
	}
}
