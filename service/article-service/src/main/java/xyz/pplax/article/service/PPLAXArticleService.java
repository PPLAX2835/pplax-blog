package xyz.pplax.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.article.dao.PPLAXArticleDao;
import xyz.pplax.article.po.Article;
import xyz.pplax.service.base.BaseService;

/**
 * article 表Service层
 */
@Service
public class PPLAXArticleService extends BaseService<Article> {
	@SuppressWarnings("unused")
	private PPLAXArticleDao articleDao;
	
	@Autowired
    public void setInfoDao(PPLAXArticleDao articleDao) {
        super.setBaseDao(articleDao);
        this.articleDao = articleDao;
    }
}