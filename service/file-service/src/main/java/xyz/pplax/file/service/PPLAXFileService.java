package xyz.pplax.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.file.dao.PPLAXFileDao;
import xyz.pplax.file.po.File;
import xyz.pplax.service.base.BaseService;

/**
 * file 表Service层
 */
@Service
public class PPLAXFileService extends BaseService<File> {
	@SuppressWarnings("unused")
	private PPLAXFileDao fileDao;
	
	@Autowired
    public void setInfoDao(PPLAXFileDao fileDao) {
        super.setBaseDao(fileDao);
        this.fileDao = fileDao;
    }
}