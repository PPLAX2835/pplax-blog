package xyz.pplax.pplaxblog.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@SpringCloudApplication
@EnableSwagger2
@ComponentScan(basePackages = {
        "xyz.pplax.pplaxblog.commons.base.handler",
        "xyz.pplax.pplaxblog.commons.config.durid",
        "xyz.pplax.pplaxblog.commons.config.mybatis",
        "xyz.pplax.pplaxblog.commons.config.swagger",
        "xyz.pplax.pplaxblog.file.restapi",
        "xyz.pplax.pplaxblog.xo.service",
        "xyz.pplax.pplaxblog.file.components",
        "xyz.pplax.pplaxblog.file.config"
})
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
