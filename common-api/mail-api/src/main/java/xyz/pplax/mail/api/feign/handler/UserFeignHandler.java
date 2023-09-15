package xyz.pplax.mail.api.feign.handler;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.pplax.admin.pojo.UserPojo;
import xyz.pplax.core.entity.R;
import xyz.pplax.core.exception.user.UserException;
import xyz.pplax.mail.api.feign.UserFeignService;


@Component
public class UserFeignHandler implements UserFeignService {

    @Override
    public R queryUserByUid(UserPojo pojo) {
        return R.failure();
    }

    @Override
    public R updateUser(UserPojo pojo) throws UserException {
        return null;
    }

    @Override
    public R bindingEmail(UserPojo pojo) throws BindException {
        return R.failure();
    }
}