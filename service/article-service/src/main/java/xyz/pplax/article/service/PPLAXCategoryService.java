package xyz.pplax.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.article.dao.PPLAXCategoryDao;
import xyz.pplax.article.po.Category;
import xyz.pplax.service.base.BaseService;

/**
 * category 表Service层
 */
@Service
public class PPLAXCategoryService extends BaseService<Category> {
	@SuppressWarnings("unused")
	private PPLAXCategoryDao categoryDao;
	
	@Autowired
    public void setInfoDao(PPLAXCategoryDao categoryDao) {
        super.setBaseDao(categoryDao);
        this.categoryDao = categoryDao;
    }
}