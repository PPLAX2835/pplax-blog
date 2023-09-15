package xyz.pplax.admin.api.feign.handler;

import org.springframework.stereotype.Component;
import xyz.pplax.admin.api.feign.EmailFeignService;
import xyz.pplax.core.entity.R;
import xyz.pplax.message.pojo.EmailPojo;


@Component
public class EmailFeignHandler implements EmailFeignService {

    @Override
    public R queryByEmailNumber(EmailPojo pojo) {
        return R.failure();
    }

    @Override
    public R queryEmailByUid(EmailPojo pojo) {
        return R.failure();
    }

    @Override
    public R insertEmail(EmailPojo pojo) {
        return R.failure();
    }
}
