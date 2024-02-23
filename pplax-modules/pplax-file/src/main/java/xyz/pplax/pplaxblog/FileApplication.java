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
        "xyz.pplax.pplaxblog.commons.handler",
        "xyz.pplax.pplaxblog.starter.durid",
        "xyz.pplax.pplaxblog.starter.mybatis",
        "xyz.pplax.pplaxblog.starter.swagger",
        "xyz.pplax.pplaxblog.starter.redis",
        "xyz.pplax.pplaxblog.xo.service.filestorage",
        "xyz.pplax.pplaxblog.xo.service.user",
        "xyz.pplax.pplaxblog.xo.service.userinfo",
        "xyz.pplax.pplaxblog.xo.service.role",
        "xyz.pplax.pplaxblog.xo.service.menu",
        "xyz.pplax.pplaxblog.file.components",
        "xyz.pplax.pplaxblog.file.controller",
        "xyz.pplax.pplaxblog.file.service",
        "xyz.pplax.pplaxblog.file.config"
})
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
