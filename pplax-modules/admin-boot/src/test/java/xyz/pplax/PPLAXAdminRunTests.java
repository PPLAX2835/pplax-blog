package xyz.pplax;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.pplax.admin.service.AdminRouterService;

@SpringBootTest
public class PPLAXAdminRunTests {

    @Autowired
    AdminRouterService adminRouterService;
    @Test
    public void AdminRouterServiceTest() {

    }



}
