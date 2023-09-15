package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.PPLAXNavigationDao;
import xyz.pplax.admin.po.Navigation;
import xyz.pplax.service.base.BaseService;

/**
 * navigation 表Service层
 */
@Service
public class PPLAXNavigationService extends BaseService<Navigation> {
	@SuppressWarnings("unused")
	private PPLAXNavigationDao navigationDao;
	
	@Autowired
    public void setInfoDao(PPLAXNavigationDao navigationDao) {
        super.setBaseDao(navigationDao);
        this.navigationDao = navigationDao;
    }
}