package xyz.pplax.pplaxblog;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
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
        "xyz.pplax.pplaxblog.feign",
        "xyz.pplax.pplaxblog.xo.service"
})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

}
