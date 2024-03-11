package xyz.pplax.pplaxblog;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@SpringCloudApplication
@EnableSwagger2
@ComponentScan(basePackages = {
        "xyz.pplax.pplaxblog.starter.security",
        "xyz.pplax.pplaxblog.starter.durid",
        "xyz.pplax.pplaxblog.starter.mybatis",
        "xyz.pplax.pplaxblog.starter.redis",
        "xyz.pplax.pplaxblog.xo.service.user",
        "xyz.pplax.pplaxblog.xo.service.userinfo",
        "xyz.pplax.pplaxblog.xo.service.role",
        "xyz.pplax.pplaxblog.xo.service.menu",
        "xyz.pplax.pplaxblog.xo.service.filestorage",
        "xyz.pplax.pplaxblog.xo.service.blog",
        "xyz.pplax.pplaxblog.xo.service.collect",
        "xyz.pplax.pplaxblog.xo.service.comment",
        "xyz.pplax.pplaxblog.xo.service.feedback",
        "xyz.pplax.pplaxblog.xo.service.tag",
        "xyz.pplax.pplaxblog.xo.service.blogsort",
        "xyz.pplax.pplaxblog.auth.controller",
        "xyz.pplax.pplaxblog.auth.config",
        "xyz.pplax.pplaxblog.auth.service"
})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
