package xyz.pplax.pplaxblog;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@SpringCloudApplication
@EnableSwagger2
@EnableFeignClients
@ComponentScan(basePackages = {
        "xyz.pplax.pplaxblog.commons.handler",
        "xyz.pplax.pplaxblog.starter.durid",
        "xyz.pplax.pplaxblog.starter.mybatis",
        "xyz.pplax.pplaxblog.starter.swagger",
        "xyz.pplax.pplaxblog.starter.redis",
        "xyz.pplax.pplaxblog.xo.service",
        "xyz.pplax.pplaxblog.file.controller",
        "xyz.pplax.pplaxblog.feign",
        "xyz.pplax.pplaxblog.file.service"
})
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
