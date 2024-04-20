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
 * @ClassName: ChatMessageConsumer
 * @Author: PPLAX
 * @Date: 2024/4/20
 **/
@Component
public class ChatMessageConsumer {

    private static Logger log = LogManager.getLogger(ChatMessageConsumer.class);

    @Autowired
    private MessageService messageService;

    @RabbitListener(queues = MqConstants.PPLAX_CHAT_MESSAGE)
    public void saveMessage(Message message) {
        log.info("接收到需要保存的聊天消息：" + message);
        messageService.save(message);
    }


}
