package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.PPLAXSocialDao;
import xyz.pplax.admin.po.Social;
import xyz.pplax.service.base.BaseService;

/**
 * social 表Service层
 */
@Service
public class PPLAXSocialService extends BaseService<Social> {
	@SuppressWarnings("unused")
	private PPLAXSocialDao socialDao;
	
	@Autowired
    public void setInfoDao(PPLAXSocialDao socialDao) {
        super.setBaseDao(socialDao);
        this.socialDao = socialDao;
    }
}