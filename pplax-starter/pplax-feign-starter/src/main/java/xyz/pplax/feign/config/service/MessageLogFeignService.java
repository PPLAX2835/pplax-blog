package xyz.pplax.feign.config.service;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.pplax.core.entity.R;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.JSONUtils;
import xyz.pplax.core.util.LogUtils;
import xyz.pplax.message.pojo.MessageLogPojo;
import xyz.pplax.message.vo.MessageLogVO;


@FeignClient(value = "pplax-message", name = "pplax-message", fallback = MessageLogFeignHandler.class)
public interface MessageLogFeignService {
    @PostMapping("/message/messageLog/insertMessageLog")
    R insertMessageLog(@RequestBody MessageLogPojo messageLog) throws BindException;

    @PostMapping("/message/messageLog/updateMessageLog")
    R updateMessageLog(@RequestBody MessageLogPojo messageLog) throws BindException;

    @PostMapping("/message/messageLog/queryMessageLogByUid")
    R queryMessageLogByUid(@RequestBody MessageLogPojo messageLog);

    @Component
    static class UpdateMessageLog {

        @Autowired
        private MessageLogFeignService messageLogFeignService;

        /**
         * 更新数据库中的mq消息的信息
         * @param correlationDataId
         * @param ackStatus
         * @param consumeStatus
         * @param errorMessage
         * @throws BindException
         */
        public void updateMessageLogInfo(String correlationDataId, boolean ackStatus,
                                          boolean consumeStatus, String errorMessage, Message message) throws BindException {
            MessageLogVO messageLogVO = null;
            try {
                R r = messageLogFeignService.queryMessageLogByUid(new MessageLogPojo(){{
                    setUid(Long.parseLong(correlationDataId));
                }});
                messageLogVO = JSONUtils.parseObjFromResult(r, "data", MessageLogVO.class);
            } catch (Exception e) {
                LogUtils.logExceptionInfo(e);
                return;
            }

            if (messageLogVO == null) {
                return;
            }

            messageLogVO.setAckStatus(ackStatus);
            messageLogVO.setConsumeStatus(consumeStatus);
            messageLogVO.setErrorMessage(errorMessage);
            messageLogFeignService.updateMessageLog(BeanUtils.copyProperties(messageLogVO, MessageLogPojo.class));
        }
    }
}
