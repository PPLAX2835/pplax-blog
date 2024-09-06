package xyz.pplax.pplaxblog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="auth-server")
public interface AuthFeignClient {

    @DeleteMapping("/pOauth/user/{userUid}/token")
    String removeToken(@PathVariable("userUid") String userUid);

}
