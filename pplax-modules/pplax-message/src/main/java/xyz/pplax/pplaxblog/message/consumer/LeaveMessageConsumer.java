package xyz.pplax.pplaxblog.message.consumer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.xo.entity.Message;
import xyz.pplax.pplaxblog.xo.service.MessageService;

/**
 * @ClassName: LeaveMessageConsumer
 * @Author: PPLAX
 * @Date: 2024/4/20
 **/
@Component
public class LeaveMessageConsumer {

    private static Logger log = LogManager.getLogger(LeaveMessageConsumer.class);

    @Autowired
    private MessageService messageService;

    @RabbitListener(queues = MqConstants.PPLAX_LEAVE_MESSAGE)
    public void saveMessage(Message message) {
        log.info("接收到需要保存的留言消息：" + message);
        messageService.save(message);
    }


}
