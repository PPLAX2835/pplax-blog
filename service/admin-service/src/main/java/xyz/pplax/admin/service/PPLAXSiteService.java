package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.PPLAXSiteDao;
import xyz.pplax.admin.po.Site;
import xyz.pplax.service.base.BaseService;

/**
 * site 表Service层
 */
@Service
public class PPLAXSiteService extends BaseService<Site> {
	@SuppressWarnings("unused")
	private PPLAXSiteDao siteDao;
	
	@Autowired
    public void setInfoDao(PPLAXSiteDao siteDao) {
        super.setBaseDao(siteDao);
        this.siteDao = siteDao;
    }
}