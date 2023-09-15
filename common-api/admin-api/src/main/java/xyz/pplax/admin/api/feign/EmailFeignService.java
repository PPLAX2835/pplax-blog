package xyz.pplax.admin.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.pplax.admin.api.feign.handler.EmailFeignHandler;
import xyz.pplax.core.entity.R;
import xyz.pplax.message.pojo.EmailPojo;


@FeignClient(value = "pplax-message", contextId = "admin-pplax-email", fallback = EmailFeignHandler.class)
public interface EmailFeignService {
    
    @PostMapping("/message/email/queryByEmailNumber")
    R queryByEmailNumber(@RequestBody EmailPojo pojo);

    @PostMapping("/message/email/queryEmailByUid")
    R queryEmailByUid(@RequestBody EmailPojo pojo);

    @PostMapping("/message/email/insertEmail")
    R insertEmail(@RequestBody EmailPojo pojo);
}
