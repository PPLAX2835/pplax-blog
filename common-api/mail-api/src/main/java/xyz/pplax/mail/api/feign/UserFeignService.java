package xyz.pplax.mail.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.pplax.admin.pojo.UserPojo;
import xyz.pplax.core.entity.R;
import xyz.pplax.core.exception.user.UserException;
import xyz.pplax.mail.api.feign.handler.UserFeignHandler;


@FeignClient(value = "pplax-admin", fallback = UserFeignHandler.class)
public interface UserFeignService {

    @PostMapping("/admin/user/queryUserByUid")
    R queryUserByUid(@RequestBody UserPojo pojo);

    @PostMapping("/admin/user/updateUser")
    R updateUser(@RequestBody UserPojo pojo) throws UserException;

    @PostMapping("/admin/user/bindingEmail")
    R bindingEmail(@RequestBody UserPojo pojo) throws BindException;
}