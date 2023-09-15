package xyz.pplax.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableAspectJAutoProxy
@EnableFeignClients
@SpringBootApplication
public class AuthServerRun {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerRun.class, args);
    }


    /**
     * PasswordEncoder              这个将来放到配置类里
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
