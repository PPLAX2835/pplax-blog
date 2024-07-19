package xyz.pplax.pplaxblog.admin.component;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.pplax.pplaxblog.starter.redis.constants.BaseRedisConstants;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.service.RoleService;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class MyInit {

    private static final Logger log = LogManager.getLogger(MyInit.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisService redisService;

    /**
     * 角色信息数据预热
     */
    @PostConstruct
    public void RoleEntityInit(){

        roleService.preheat();

    }

    /**
     * 记录运行时间
     */
    @PostConstruct
    public void RuntimeRecord(){

        redisService.setCacheObject(BaseRedisConstants.START_RUN_TIME, new Date());

    }
}