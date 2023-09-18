package xyz.pplax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class PPLAXCommentRun {
    public static void main(String[] args) {
        SpringApplication.run(PPLAXCommentRun.class, args);
    }
}
