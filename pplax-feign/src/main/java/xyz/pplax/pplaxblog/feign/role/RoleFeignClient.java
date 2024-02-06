package xyz.pplax.pplaxblog.feign.role;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="admin-server")
public interface RoleFeignClient {

    @GetMapping("/role/{uid}")
    String getByUid(@PathVariable("uid") String uid);

}
