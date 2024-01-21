package xyz.pplax.pplaxblog.base.controller;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller基类
 */
public class SuperController {

	/**
	 * 将map转换成json字符串
	 * @param map
	 * @return
	 */
	public String toJson(Map<String, Object> map) {
		return JSONObject.fromObject(map).toString();
	}

	public <T> String toJson(List<T> list) {
		return JSONObject.fromObject(list).toString();
	}

	/**
	 * 获取一个map
	 * @return
	 */
	public static Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
}
