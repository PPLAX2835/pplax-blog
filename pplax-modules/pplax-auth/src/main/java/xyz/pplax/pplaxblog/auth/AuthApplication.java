package xyz.pplax.pplaxblog.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {
        "xyz.pplax.pplaxblog.auth.config",
        "xyz.pplax.pplaxblog.auth.service",
        "xyz.pplax.pplaxblog.commons.durid",
//        "xyz.pplax.pplaxblog.commons.exception",      // 扫描这个在访问/oauth/authorize时就会抛异常，还不明白原理
        "xyz.pplax.pplaxblog.commons.mybatis",
        "xyz.pplax.pplaxblog.xo.service"
})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
