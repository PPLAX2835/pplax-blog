package xyz.pplax.mail.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.pplax.core.entity.R;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.mail.api.feign.handler.FileFeignHandler;


@FeignClient(value = "pplax-file", name = "pplax-file", fallback = FileFeignHandler.class)
public interface FileFeignService {

    @PostMapping("/file/queryListFileByCondition")
    R queryListFileByCondition(@RequestBody Condition<Long> condition);
}
