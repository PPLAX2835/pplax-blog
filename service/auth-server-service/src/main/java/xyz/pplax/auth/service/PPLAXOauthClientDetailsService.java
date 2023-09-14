package xyz.pplax.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.auth.dao.PPLAXOauthClientDetailsDao;
import xyz.pplax.auth.po.OauthClientDetails;
import xyz.pplax.service.base.BaseService;

/**
 * oauth_client_details 表Service层
 */
@Service
public class PPLAXOauthClientDetailsService extends BaseService<OauthClientDetails> {
	@SuppressWarnings("unused")
	private PPLAXOauthClientDetailsDao oauthClientDetailsDao;
	
	@Autowired
    public void setInfoDao(PPLAXOauthClientDetailsDao oauthClientDetailsDao) {
        super.setBaseDao(oauthClientDetailsDao);
        this.oauthClientDetailsDao = oauthClientDetailsDao;
    }
}