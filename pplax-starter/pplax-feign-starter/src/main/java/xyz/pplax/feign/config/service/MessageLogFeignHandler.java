package xyz.pplax.feign.config.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.pplax.core.entity.R;
import xyz.pplax.message.pojo.MessageLogPojo;


@Component
public class MessageLogFeignHandler implements MessageLogFeignService {

    @Override
    public R insertMessageLog(MessageLogPojo messageLog) throws BindException {
        return R.failure();
    }

    @Override
    public R updateMessageLog(MessageLogPojo messageLog) throws BindException {
        return R.failure();
    }

    @Override
    public R queryMessageLogByUid(MessageLogPojo messageLog) {
        return R.failure();
    }
}
