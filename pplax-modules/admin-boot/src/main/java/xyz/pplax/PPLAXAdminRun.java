package xyz.pplax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import xyz.pplax.admin.service.PermissionRelationService;
import xyz.pplax.admin.service.WhiteUrlService;
import xyz.pplax.data.entity.Condition;
//import xyz.pplax.admin.service.PermissionRelationService;
//import xyz.pplax.admin.service.WhiteUrlService;
//import xyz.pplax.data.entity.Condition;


@SpringBootApplication
@EnableFeignClients
public class PPLAXAdminRun {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(PPLAXAdminRun.class, args);

        // 将角色权限信息和白名单存入redis，实际上村到redis的逻辑还没写
        Condition<Long> condition = new Condition<>();
        condition.setPageSize(10000);
        run.getBean(PermissionRelationService.class).loadAllRolePermission(condition);
        run.getBean(WhiteUrlService.class).queryListWhiteUrlByCondition(new Condition<>(){{
            setPageSize(10000);
        }});
    }
}
