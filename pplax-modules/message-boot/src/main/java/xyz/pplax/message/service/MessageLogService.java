package xyz.pplax.message.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import xyz.pplax.amqp.api.AmqpSenderService;
import xyz.pplax.core.exception.mq.RabbitMQException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.ValidationUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;
import xyz.pplax.message.po.MessageLog;
import xyz.pplax.message.pojo.MessageLogPojo;
import xyz.pplax.message.vo.MessageLogVO;
import xyz.pplax.starter.properties.PPLAXProperties;

import javax.annotation.Resource;


@Service
public class MessageLogService {

    @Resource
    private PPLAXMessageLogService pplaxMessageLogService;
    @Resource
    private PPLAXProperties pplaxProperties;
    @Autowired
    private AmqpSenderService amqpSenderService;

    public void insertMessageLog(MessageLogPojo messageLog) throws BindException {
        ValidationUtils.valid(messageLog, Insert.class);
        Assert.notNull(messageLog, "插入mq消息不能为null");
        pplaxMessageLogService.insert(BeanUtils.copyProperties(messageLog, MessageLog.class));
    }

    public int physicalDeleteMessageLog(long uid) {
        return pplaxMessageLogService.deleteById(uid);
    }

    public int updateMessageLog(MessageLogPojo messageLog) throws BindException {
        ValidationUtils.valid(messageLog, Update.class);
        // 如果是从未消费，未应答修改为应答，消费，先查询是否应答了
        MessageLog query = pplaxMessageLogService.queryById(messageLog.getUid());
        if (messageLog.getAckStatus() != null && messageLog.getAckStatus()) {
            if (query.getAckStatus()) {
                throw new RabbitMQException("应答失败，此条MQ已被系统应答");
            }
        }
        if (messageLog.getConsumeStatus() != null && messageLog.getConsumeStatus()) {
            if (query.getConsumeStatus()) {
                throw new RabbitMQException("消费失败，此条MQ已被系统消费");
            }
        }
        //如果修改成功，返回最新的数据
        return pplaxMessageLogService.updateById(BeanUtils.copyProperties(messageLog, MessageLog.class));
    }

    public PageData<MessageLogVO> queryListMessageLogByCondition(Condition<Long> condition) {
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        return PageUtils.copyPageDataResult(pplaxMessageLogService.queryListByCondition(condition),MessageLogVO.class);
    }

    public MessageLogVO queryMessageLogByUid(long uid) {
        MessageLog messageLog = new MessageLog();
        messageLog.setUid(uid);
        messageLog.setConsumeStatus(null);
        return BeanUtils.copyProperties(pplaxMessageLogService.queryOne(messageLog),MessageLogVO.class);
    }

    public void resendMqMessage(long uid) throws BindException {
        MessageLogVO messageLogVO = queryMessageLogByUid(uid);
        AssertUtils.stateThrow(messageLogVO != null, () -> new RuntimeException("没有此mq消息"));

        // 重新发送
        amqpSenderService.sendMQMsg(messageLogVO.getMessage(), messageLogVO.getExchange(),
                messageLogVO.getRoutingKey(), messageLogVO.getExchangeType());
    }
}
