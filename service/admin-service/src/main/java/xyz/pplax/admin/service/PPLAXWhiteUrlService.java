package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.PPLAXWhiteUrlDao;
import xyz.pplax.admin.po.WhiteUrl;
import xyz.pplax.service.base.BaseService;

/**
 * white_url 表Service层
 */
@Service
public class PPLAXWhiteUrlService extends BaseService<WhiteUrl> {
	@SuppressWarnings("unused")
	private PPLAXWhiteUrlDao whiteUrlDao;
	
	@Autowired
    public void setInfoDao(PPLAXWhiteUrlDao whiteUrlDao) {
        super.setBaseDao(whiteUrlDao);
        this.whiteUrlDao = whiteUrlDao;
    }
}