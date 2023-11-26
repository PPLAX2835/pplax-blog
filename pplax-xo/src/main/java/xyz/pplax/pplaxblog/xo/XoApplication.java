package xyz.pplax.pplaxblog.xo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = {
        "xyz.pplax.pplaxblog.xo.controller",
        "xyz.pplax.pplaxblog.xo.service"})
public class XoApplication {
    public static void main(String[] args) {
        SpringApplication.run(XoApplication.class, args);
    }
}
