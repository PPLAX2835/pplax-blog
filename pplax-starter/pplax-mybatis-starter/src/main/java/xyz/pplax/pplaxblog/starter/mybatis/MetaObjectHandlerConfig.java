package xyz.pplax.pplaxblog.starter.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import xyz.pplax.pplaxblog.commons.base.global.BaseSQLConf;

import java.util.Date;

@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {
	
  Logger log = LogManager.getLogger(MetaObjectHandlerConfig.class);	
  @Override
  public void insertFill(MetaObject metaObject) {
	log.info("插入方法填充");
    setFieldValByName(BaseSQLConf.CREATE_TIME, new Date(), metaObject);
    setFieldValByName(BaseSQLConf.UPDATE_TIME, new Date(), metaObject);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
	  log.info("更新方法填充");
	  setFieldValByName(BaseSQLConf.UPDATE_TIME, new Date(), metaObject);
  }
}
