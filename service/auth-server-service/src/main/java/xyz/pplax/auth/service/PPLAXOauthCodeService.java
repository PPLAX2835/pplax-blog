package xyz.pplax.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.auth.dao.PPLAXOauthCodeDao;
import xyz.pplax.auth.po.OauthCode;
import xyz.pplax.service.base.BaseService;

/**
 * oauth_client_details 表Service层
 */
@Service
public class PPLAXOauthCodeService extends BaseService<OauthCode> {
    @SuppressWarnings("unused")
    private PPLAXOauthCodeDao oauthCodeDao;

    @Autowired
    public void setInfoDao(PPLAXOauthCodeDao oauthCodeDao) {
        super.setBaseDao(oauthCodeDao);
        this.oauthCodeDao = oauthCodeDao;
    }
}