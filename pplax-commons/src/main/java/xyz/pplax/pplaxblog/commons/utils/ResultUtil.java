package xyz.pplax.pplaxblog.commons.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * 返回统一接口
 */
public class ResultUtil {
	/**
	 * @param code success error
	 * @param data 返回的数据
	 * @return
	 */
	public static String result(String code,Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", data);
		return JsonUtils.objectToJson(map);
	}

}
