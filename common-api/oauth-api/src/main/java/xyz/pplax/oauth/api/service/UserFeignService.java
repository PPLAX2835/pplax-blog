package xyz.pplax.oauth.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.pplax.admin.pojo.UserPojo;
import xyz.pplax.core.entity.R;
import xyz.pplax.oauth.api.service.handler.UserFeignHandler;


@FeignClient(value = "pplax-admin", name = "pplax-admin",
        contextId = "authUserFeignServer", fallback = UserFeignHandler.class)
public interface UserFeignService {

    @PostMapping(value = "/admin/user/queryUserByUsernameContainPassword", headers = "content-type=application/json;charset=UTF-8;")
    // @RequestMapping
    R queryUserByUsernameContainPassword(@RequestBody UserPojo pojo);
}
