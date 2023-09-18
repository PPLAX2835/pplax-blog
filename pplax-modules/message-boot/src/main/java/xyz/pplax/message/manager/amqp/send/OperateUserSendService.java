package xyz.pplax.message.manager.amqp.send;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.pplax.amqp.comstant.AmqpExchangeNameConstant;
import xyz.pplax.amqp.comstant.AmqpQueueNameConstant;
import xyz.pplax.core.util.ConvertObjectUtils;
import xyz.pplax.core.util.id.GenerateInfoUtils;
import xyz.pplax.message.po.Email;
import xyz.pplax.message.po.MessageLog;
import xyz.pplax.starter.properties.PPLAXProperties;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO 此类暂时不用
 */
@Slf4j
@Component
public class OperateUserSendService {
    @Autowired
    private PPLAXProperties pplaxProperties;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /*@Autowired
    private MessageLogService messageLogService;*/

    /**
     * 当用户向数据库中插入邮箱记录之后，会调用此发送服务向PPLAX_SEND_OPERATE_USER_EXCHANGE交换机发送绑定用户账户信息的mq消息
     * 在pplax-admin模块中，和此OPERATE_USER_BINDING_EMAIL_ROUTING_KEY绑定的队列的消费和能够更新数据库中，此userUid所对应的记录，然后
     * ，当更新成功之后，再从pplax-admin模块发送验证账户的信息到PPLAX_SEND_EMAIL_COMMON_EXCHANGE交换机
     * <p>因为是跨服务进行操作，所以需要绑定seata的xid，以确保数据的一致性</p>
     * @param email
     * @throws BindException
     */
    public void sendBindingEmail(Email email, String xid) throws BindException {
        // 生成一个唯一uid 此uid用于存放消息投递记录中的uid字段
        long uid = GenerateInfoUtils.generateUid(pplaxProperties.getSnowFlakeWorkerId(), pplaxProperties.getSnowFlakeDatacenterId());
        CorrelationData correlationData = new CorrelationData(uid + "");

        Map<String,Object> messageMap = new HashMap<>();
        messageMap.put("xid",xid);
        messageMap.put("correlationDataId",correlationData.getId());
        messageMap.put("emailDO", email);

        //将发送的回复评论数据组装成一个map集合
        String jsonToString = ConvertObjectUtils.jsonToString(messageMap);
        log.error("sendBindingEmail恢复全局事务{}", RootContext.getXID());
        RootContext.bind(xid);
        insertMessageLog(jsonToString,uid);
        log.error("sendBindingEmail挂起全局事务{}", RootContext.getXID());
        RootContext.unbind();
        // 存储验证信息
        rabbitTemplate.send(AmqpExchangeNameConstant.PPLAX_SEND_OPERATE_USER_EXCHANGE,
                AmqpQueueNameConstant.OPERATE_USER_BINDING_EMAIL_ROUTING_KEY,
                new Message(jsonToString.getBytes(StandardCharsets.UTF_8)),correlationData);
    }

    private void insertMessageLog(String jsonToString, long uid) throws BindException {
        /*//向au_message_log表中插入生产信息
        MessageLog messageLogDO = setMessageLogDO(jsonToString, uid, AmqpQueueNameConstant.PPLAX_SEND_MAIL_EXCHANGE, "",
                AmqpQueueNameConstant.MAIL_VERIFY_ACCOUNT_NOTICE_ROUTING_KEY, false, 0, "topic",
                false, "");

        // 验证messageLogDO对象属性是否合法
        ValidationUtils.valid(messageLogDO, Insert.class, Default.class);
        //messageLogService.insertMessageLog(messageLogDO);*/
    }

    private MessageLog setMessageLogDO(String message, long uid, String exchange,
                                       String queue, String routingKey, boolean ackStatus,
                                       int tryCount, String exchangeType, boolean consumeStatus,
                                       String errorMessage) {
        MessageLog messageLog = new MessageLog();
        messageLog.setMessage(message);
        messageLog.setUid(uid);
        messageLog.setExchange(exchange);
        messageLog.setQueue(queue);
        messageLog.setRoutingKey(routingKey);
        messageLog.setAckStatus(ackStatus);
        messageLog.setTryCount(tryCount);
        messageLog.setExchangeType(exchangeType);
        messageLog.setConsumeStatus(consumeStatus);
        messageLog.setErrorMessage(errorMessage);
        return messageLog;
    }
}
