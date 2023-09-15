package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.SiteSettingDao;
import xyz.pplax.admin.po.SiteSetting;
import xyz.pplax.service.base.BaseService;

/**
 * site_setting 表Service层
 */
@Service
public class PPLAXSiteSettingService extends BaseService<SiteSetting> {
	@SuppressWarnings("unused")
	private SiteSettingDao siteSettingDao;
	
	@Autowired
    public void setInfoDao(SiteSettingDao siteSettingDao) {
        super.setBaseDao(siteSettingDao);
        this.siteSettingDao = siteSettingDao;
    }
}