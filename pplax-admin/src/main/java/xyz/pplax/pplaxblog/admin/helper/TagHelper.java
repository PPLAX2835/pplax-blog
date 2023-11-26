package xyz.pplax.pplaxblog.admin.helper;

import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.utils.StrUtils;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.service.TagSO;
import xyz.pplax.pplaxblog.base.enums.EStatus;
import xyz.pplax.pplaxblog.base.helper.BaseHelper;
import xyz.pplax.pplaxblog.base.jedis.JedisClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 标签管理Helper
 */
public class TagHelper extends BaseHelper{
	
	static TagSO tagSO;
	static JedisClient jedisClient;
		
	private static TagSO getTagSO () {
		if (tagSO == null) {
			tagSO = getApplicationContext().getBean(TagSO.class);
		}
		return tagSO;
	}
	private static JedisClient getJedisClient () {
		if (jedisClient == null) {
			jedisClient = getApplicationContext().getBean(JedisClient.class);
		}
		return jedisClient;
	}
	
	/**
	 * 获取标签list
	 * @return
	 */
	public static Map<String, Object> getList(Map<String, Object> model) {
		Map<String, Object> map = getMap();
		map.put(SysConf.status, EStatus.ENABLE);
		List<Tag> list= getTagSO().getList(map);
		model.put(SysConf.rows, list);
		model.put(SysConf.total, list.size());
		return model;
	}
	
	/**
	 * 搜索歌手list
	 * @author xuzhixiang
	 * @date 2017年10月2日13:32:35
	 * @return
	 */
	public static Map<String, Object> getSearchList(Map<String, Object> model, String keyword) {
		Map<String, Object> map = getMap();
		if(StrUtils.isNotEmpty(keyword)) {
			map.put(SysConf.keyword, keyword);
		}	
		List<Tag> list= getTagSO().getList(map);
		List<Tag> newlist= new ArrayList<Tag>();
		for(Tag tag : list) {
			if(tag.getStatus() != EStatus.DISABLED) {
				newlist.add(tag);
			}
		}
		model.put(SysConf.rows, newlist);
		model.put(SysConf.total, newlist.size());
		return model;
	}
	
	/**
	 * 清空Redis中String类型的缓存
	 * @param key
	 */
	public static void emptyRedisString(String key) {
		getJedisClient().del(key);
	}
	
	/**
	 * 清空Redis中Hash类型的缓存
	 * @param hashtable
	 * @param key
	 */
	public static void emptyRedisHash(String hashtable, String key) {
		getJedisClient().hdel(hashtable, key);
	}
}
