package xyz.pplax.pplaxblog.admin.helper;

import xyz.pplax.pplaxblog.admin.global.SysConf;
import xyz.pplax.pplaxblog.admin.utils.SpringContextUtils;
import xyz.pplax.pplaxblog.utils.StrUtils;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.service.BlogSO;
import xyz.pplax.pplaxblog.xo.service.TagSO;
import xyz.pplax.pplaxblog.base.enums.EStatus;
import xyz.pplax.pplaxblog.base.helper.BaseHelper;
import xyz.pplax.pplaxblog.base.jedis.JedisClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 博客管理Helper
 */
public class BlogHelper extends BaseHelper{

	static BlogSO blogSO;
	static TagSO tagSO;
	static HttpSolrServer  httpSolrServer;
	static JedisClient jedisClient;

	private static BlogSO getBlogSO() {
		if (blogSO == null) {
			blogSO = SpringContextUtils.getBean(BlogSO.class);

		}
		return blogSO;
	}

	private static TagSO getTagSO() {
		if (tagSO == null) {
			tagSO =  SpringContextUtils.getBean(TagSO.class);
		}
		return tagSO;
	}

	private static HttpSolrServer getHttpSolrServer() {
		if (httpSolrServer == null) {
			httpSolrServer =  SpringContextUtils.getBean(HttpSolrServer.class);
		}
		return httpSolrServer;
	}

	private static JedisClient getJedisClient () {
		if (jedisClient == null) {
			jedisClient =  SpringContextUtils.getBean(JedisClient.class);
		}
		return jedisClient;
	}

	/**
	 * 获取博客list
	 * @return
	 */
	public static Map<String, Object> getList(Map<String, Object> model) {
		Map<String, Object> map = getMap();
		map.put(SysConf.status, EStatus.ENABLE);
		List<Blog> list= getBlogSO().getList(map);
		model.put(SysConf.rows, list);
		model.put(SysConf.total, list.size());
		return model;
	}

	/**
	 * 搜索博客list
	 * @author xuzhixiang
	 * @date 2017年10月2日13:32:35
	 * @return
	 */
	public static Map<String, Object> getSearchList(Map<String, Object> model, String keyword) {
		Map<String, Object> map = getMap();
		if(StrUtils.isNotEmpty(keyword)) {
			map.put(SysConf.keyword, keyword);
		}
		map.put(SysConf.orderBy, SysConf.order + " " + SysConf.by + " " + SysConf.status+ " " + SysConf.desc);
		List<Blog> list= getBlogSO().getList(map);
		List<Blog> newlist = new ArrayList<Blog>();
		for(Blog blog : list) {
			Tag tag = getTagSO().get(blog.getTaguid());
			if(tag != null) {
				blog.setTagname(tag.getContent());
			}
			if(blog.getStatus() != EStatus.DISABLED) {
				newlist.add(blog);
			}
		}
		model.put(SysConf.rows, newlist);
		model.put(SysConf.total, newlist.size());
		return model;
	}

	/**
	 * 将Blog生成索引
	 * @param blog
	 * @return
	 * @throws IOException
	 * @throws SolrServerException
	 */
	public static void insertIndex(Blog blog) throws SolrServerException, IOException {

		SolrInputDocument document = new SolrInputDocument();
        document.addField(SysConf.id, blog.getUid());
        document.addField(SysConf.uid, blog.getUid());
        document.addField(SysConf.oid, blog.getOid());
        document.addField(SysConf.title, blog.getTitle());
        document.addField(SysConf.summary, blog.getSummary());
        document.addField(SysConf.content, blog.getContent());
        document.addField(SysConf.taguid, blog.getTitle());
        document.addField(SysConf.clickcount, blog.getClickcount());
        document.addField(SysConf.collectcount, blog.getCollectcount());
        document.addField(SysConf.photo, blog.getPhoto());
        document.addField(SysConf.createtime, blog.getCreatetime());
        document.addField(SysConf.updatetime, blog.getUpdatetime());
        getHttpSolrServer().add(document);
        getHttpSolrServer().commit();
	}

	/**
	 * 通过id删除索引
	 * @param id
	 * @throws IOException
	 * @throws SolrServerException
	 */
	public static void deleteIndex(String id) throws SolrServerException, IOException {
		getHttpSolrServer().deleteById(id);
		getHttpSolrServer().commit();
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
