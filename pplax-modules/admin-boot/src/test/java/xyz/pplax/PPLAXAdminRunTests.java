package xyz.pplax;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.pplax.admin.dto.AdminRouterDTO;
import xyz.pplax.admin.pojo.RouterPermissionPojo;
import xyz.pplax.admin.service.AdminRouterService;
import xyz.pplax.admin.service.PermissionAdminRouterRelationshipService;

import java.util.List;

@SpringBootTest
public class PPLAXAdminRunTests {

    @Autowired
    AdminRouterService adminRouterService;
    @Test
    public void AdminRouterServiceTest() {

    }


    @Autowired
    PermissionAdminRouterRelationshipService permissionAdminRouterRelationshipService;
    @Test
    public void PermissionAdminRouterRelationshipServiceTest() {
        RouterPermissionPojo routerPermissionPojo = new RouterPermissionPojo();
        routerPermissionPojo.setUid(12L);
        routerPermissionPojo.setPermissionUid(12L);
        List<AdminRouterDTO> adminRouterDTOS = permissionAdminRouterRelationshipService.queryAllAdminRouterInfoByPermissionUid(routerPermissionPojo);
        System.out.println(adminRouterDTOS);
    }



}
