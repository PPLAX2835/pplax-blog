package xyz.pplax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootTest
public class PPLAXCommentRunTests {
    public static void main(String[] args) {
        SpringApplication.run(PPLAXCommentRunTests.class, args);
    }
}
