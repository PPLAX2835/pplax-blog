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
        "xyz.pplax.pplaxblog.commons",
        "xyz.pplax.pplaxblog.file.restapi",
        "xyz.pplax.pplaxblog.xo.service",
        "xyz.pplax.pplaxblog.file.components"
})
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
