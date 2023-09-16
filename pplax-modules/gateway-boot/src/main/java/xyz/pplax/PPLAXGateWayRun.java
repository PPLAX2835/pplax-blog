package xyz.pplax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author qsyyke
 */

@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = {"xyz.pplax.core.exception.*", "xyz.pplax.starter.interceptor.*","xyz.pplax.starter.manager.advice.*"})})
@SpringBootApplication
public class PPLAXGateWayRun {
    public static void main(String[] args) {
        SpringApplication.run(PPLAXGateWayRun.class, args);
    }

    // @Bean
    // public CorsWebFilter corsFilter() {
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     CorsConfiguration config = new CorsConfiguration();
    //     config.addAllowedOrigin("*");
    //     config.setAllowCredentials(false);
    //     config.addAllowedHeader("*");
    //     config.addAllowedMethod("*");
    //     source.registerCorsConfiguration("/**", config);
    //     return new CorsWebFilter(source);
    // }
}
