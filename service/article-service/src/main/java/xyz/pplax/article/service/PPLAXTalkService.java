package xyz.pplax.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.article.dao.PPLAXTalkDao;
import xyz.pplax.article.po.Talk;
import xyz.pplax.service.base.BaseService;

/**
 * talk 表Service层
 */
@Service
public class PPLAXTalkService extends BaseService<Talk> {
	@SuppressWarnings("unused")
	private PPLAXTalkDao talkDao;
	
	@Autowired
    public void setInfoDao(PPLAXTalkDao talkDao) {
        super.setBaseDao(talkDao);
        this.talkDao = talkDao;
    }
}