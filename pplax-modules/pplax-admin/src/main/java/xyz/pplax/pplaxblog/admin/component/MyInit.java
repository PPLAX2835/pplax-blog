package xyz.pplax.pplaxblog.admin.component;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.pplax.pplaxblog.starter.redis.constants.BaseRedisConstants;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.constants.redis.RoleRedisConstants;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.service.role.RoleService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class MyInit {

    private static final Logger log = LogManager.getLogger(MyInit.class);

    @Autowired
    private RoleService roleService;

    /**
     * 角色信息数据预热
     */
    @PostConstruct
    public void RoleEntityInit(){

        roleService.preheat();

    }
}