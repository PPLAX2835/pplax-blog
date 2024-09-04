package xyz.pplax.pplaxblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@SpringCloudApplication
@EnableSwagger2
@EnableFeignClients
@ServletComponentScan
@ComponentScan(basePackages = {
        "xyz.pplax.pplaxblog.starter.security",
        "xyz.pplax.pplaxblog.starter.durid",
        "xyz.pplax.pplaxblog.starter.mybatis",
        "xyz.pplax.pplaxblog.starter.redis",
        "xyz.pplax.pplaxblog.starter.amqp",
        "xyz.pplax.pplaxblog.auth.controller",
        "xyz.pplax.pplaxblog.auth.config",
        "xyz.pplax.pplaxblog.auth.service",
        "xyz.pplax.pplaxblog.auth.handler",
        "xyz.pplax.pplaxblog.feign",
        "xyz.pplax.pplaxblog.xo.service",
        "xyz.pplax.pplaxblog.xo.component.filter"
})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
