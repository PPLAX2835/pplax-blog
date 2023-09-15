package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.PPLAXSettingDao;
import xyz.pplax.admin.po.Setting;
import xyz.pplax.service.base.BaseService;

/**
 * setting 表Service层
 */
@Service
public class PPLAXSettingService extends BaseService<Setting> {
	@SuppressWarnings("unused")
	private PPLAXSettingDao pplaxSettingDao;
	
	@Autowired
    public void setInfoDao(PPLAXSettingDao pplaxSettingDao) {
        super.setBaseDao(pplaxSettingDao);
        this.pplaxSettingDao = pplaxSettingDao;
    }
}