package xyz.pplax.pplaxblog.admin.component;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.pplax.pplaxblog.xo.service.RoleService;

import javax.annotation.PostConstruct;

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