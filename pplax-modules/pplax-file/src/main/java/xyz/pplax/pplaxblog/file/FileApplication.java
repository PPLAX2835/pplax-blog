package xyz.pplax.pplaxblog.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {
        "xyz.pplax.pplaxblog.config.durid",
        "xyz.pplax.pplaxblog.config.handler",
        "xyz.pplax.pplaxblog.config.minio",
        "xyz.pplax.pplaxblog.config.mybatis",
        "xyz.pplax.pplaxblog.config.swagger",
        "xyz.pplax.pplaxblog.file.restapi",
        "xyz.pplax.pplaxblog.xo.service",
        "xyz.pplax.pplaxblog.file.components"
})
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
