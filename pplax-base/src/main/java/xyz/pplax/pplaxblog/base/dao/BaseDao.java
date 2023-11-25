package xyz.pplax.pplaxblog.base.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Dao基类
 */
@Repository
public interface BaseDao<T>{
	
	/**
	 * ͨ通过uid获取T
	 * @param uid
	 * @return
	 */
	public T get(String uid);
	
	/**
	 * ͨ通过map获取一个T
	 * @param map
	 * @return
	 */
	public T getOne(Map<String, Object> map);
	
	/**
	 * ͨ通过map获取数量
	 * @param map
	 * @return
	 */
	public int getCount(Map<String, Object> map);
	
	/**
	 * ͨ通过map获取列表
	 * @param map
	 * @return
	 */
	public List<T> getList(Map<String, Object> map);
	
	/**
	 * 插入T
	 * @param map
	 * @return
	 */
	public boolean insert(T t);
	
	/**
	 * 更新T
	 * @param t
	 * @return
	 */
	public boolean update(T t);
	
	/**
	 * 通过Map更新T
	 * @param map
	 * @return
	 */
	public boolean updateFields(Map<String, Object> map);
	
	/**
	 * 删除T
	 * @param uid
	 * @return
	 */
	public boolean delete(String uid);
}
