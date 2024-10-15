package xyz.pplax.pplaxblog.starter.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {
	
    Logger log = LogManager.getLogger(MetaObjectHandlerConfig.class);

    private static final String UPDATE_TIME_CAMEL = "updateTime";

    private static final String CREATE_TIME_CAMEL = "createTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("插入方法填充");
        setFieldValByName(CREATE_TIME_CAMEL, new Date(), metaObject);
        setFieldValByName(UPDATE_TIME_CAMEL, new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新方法填充");
        setFieldValByName(UPDATE_TIME_CAMEL, new Date(), metaObject);
    }
}
