package xyz.pplax.pplaxblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableTransactionManagement
@SpringCloudApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
//@EnableSwagger2
@ComponentScan(basePackages = {
    "xyz.pplax.pplaxblog.starter.security",
    "xyz.pplax.pplaxblog.gateway.config",
    "xyz.pplax.pplaxblog.gateway.properties",
    "xyz.pplax.pplaxblog.gateway.handler",
    "xyz.pplax.pplaxblog.gateway.security",
    "xyz.pplax.pplaxblog.feign.role"
})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
