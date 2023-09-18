package xyz.pplax.admin.manager.mq.consumer;

import com.rabbitmq.client.Channel;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.pplax.admin.api.feign.EmailFeignService;
import xyz.pplax.admin.manager.task.LoadRolePermissionInfo;
import xyz.pplax.admin.manager.task.LoadWhiteUrlInfo;
import xyz.pplax.admin.pojo.UserPojo;
import xyz.pplax.admin.service.PermissionRelationService;
import xyz.pplax.admin.service.UserService;
import xyz.pplax.admin.vo.UserVO;
import xyz.pplax.amqp.comstant.AmqpExchangeNameConstant;
import xyz.pplax.amqp.comstant.AmqpQueueNameConstant;
import xyz.pplax.amqp.config.service.MistakeMessageSendService;
import xyz.pplax.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.pplax.api.mail.sendmail.service.SendMQMessageService;
import xyz.pplax.api.mail.sendmail.util.AccountInfoUtils;
import xyz.pplax.api.mail.sendmail.util.StorageEmailVerifyUrlUtil;
import xyz.pplax.api.mail.sendmail.util.StorageMailUtils;
import xyz.pplax.core.entity.R;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.email.EmailException;
import xyz.pplax.core.exception.user.UserException;
import xyz.pplax.core.util.ConvertObjectUtils;
import xyz.pplax.core.util.JSONUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.feign.config.service.MessageLogFeignService;
import xyz.pplax.message.pojo.EmailPojo;
import xyz.pplax.message.vo.EmailVO;
import xyz.pplax.starter.properties.PPLAXProperties;

/**
 * 这是一个消费操作用户信息的mq消费者，比如绑定用户信息，向redis中，添加角色权限信息等缓存
 */
@Component
public class OperateUserConsumer {

    private final String enableAccountKey = "enableAccount";

    @Autowired
    private MessageLogFeignService.UpdateMessageLog updateMessageLog;
    @Autowired
    private MistakeMessageSendService mistakeMessageSendService;
    @Autowired
    private UserService userService;
    @Autowired
    private PPLAXProperties.PPLAXAccountProperties pplaxAccountProperties;
    @Autowired
    private SendMQMessageService sendMQMessageService;
    @Autowired
    private LoadRolePermissionInfo loadRolePermissionInfo;
    @Autowired
    private LoadWhiteUrlInfo loadWhiteUrlInfo;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private PermissionRelationService permissionRelationService;

    @Autowired
    private EmailFeignService emailFeignService;

    /**
     * 当用户在认证服务中，登录次数达到最大值之后，认证服务会向mq中发送消息，禁用该用户
     * @param msgJson
     * @param channel
     * @param message
     * @throws Exception
     */
    @GlobalTransactional
    @RabbitListener(queues = AmqpQueueNameConstant.OPERATE_USER_LOCK_ACCOUNT_QUEUE, ackMode = "MANUAL")
    public void lockAccountConsumer(String msgJson, Channel channel, Message message) throws Exception {
        // 待替换的key和value的map
        if (msgJson == null) {
            // 错误消息
            mistakeMessageSendService.sendMistakeMessageToExchange(msgJson, channel, message);
        }

        // 根据用户名查询用户信息
        UserVO userVO = userService.queryUserByUsername(msgJson);
        if (userVO == null || !userVO.getVerifyEmail()) {
            // 不做处理
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        if (userVO.getEmailUid() == null) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        // 如果账户已经被锁住，则不处理
        if (userVO.getAccountLock()) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        // 用户存在 修改锁住状态
        UserPojo userPojo = new UserPojo();
        userPojo.setUid(userVO.getUid());
        userPojo.setAccountLock(true);
        int updateUserNum = 0;
        try {
            updateUserNum = userService.updateUser(userPojo);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
        if (updateUserNum == 1) {
            boolean sendStatus = sendEnableAccountEmail(userVO);
            AssertUtils.stateThrow(sendStatus,
                    () -> new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_RECEIVE_SEND_FAILURE));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        // 消费失败
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
    }

    /**
     * 更新角色权限信息的消费者，如果redis中的角色权限信息过期，会向mq中发送消息，从新将角色权限信息添加到redis中
     * @param msgJson 发送的消息，可以发送任意字符，不会使用到该消息
     * @param channel
     * @param message
     * @throws Exception
     */
    @RabbitListener(queues = AmqpQueueNameConstant.UPDATE_ROLE_PERMISSION_CACHE_QUEUE, ackMode = "MANUAL")
    public void updateRolePermissionCacheConsumer(String msgJson, Channel channel, Message message) throws Exception {
        // loadRolePermissionInfo.storagePermissionInfoToRedis();
        permissionRelationService.loadAllRolePermission(new Condition<>(){{
            setPageSize(10000);
        }});
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * mq发送消息，更新redis中的白名单缓存
     * @param msgJson 任意消息，不使用
     * @param channel
     * @param message
     * @throws Exception
     */
    @RabbitListener(queues = AmqpQueueNameConstant.UPDATE_WHITE_URL_CACHE_QUEUE, ackMode = "MANUAL")
    public void updateWhiteUrlCacheConsumer(String msgJson, Channel channel, Message message) throws Exception {
        loadWhiteUrlInfo.storageWhiteUrlInfoToRedis();
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 发送一封邮件到该用户所对应的邮箱中，用户点击发送的链接进行解除账户锁住状态
     * @param userVO
     * @return
     */
    private boolean sendEnableAccountEmail(UserVO userVO) throws BindException {
        String verifyUrl = AccountInfoUtils.generateVerifyUrl(userVO.getUid(),
                enableAccountKey, userVO.hashCode(), pplaxAccountProperties.getEnableAccountPrefixPath());

        // 将验证的信息存入redis
        boolean storageVerifyUrlToRedis = StorageEmailVerifyUrlUtil.storageVerifyUrlToRedis(enableAccountKey, userVO.hashCode(),
                pplaxAccountProperties.getMailVerifyAccountExpirationTime(), userVO.getUid());
        // 如果存储成功，则发送邮件
        if (!storageVerifyUrlToRedis) {
            return false;
        }

        EmailPojo emailPojo = new EmailPojo();
        emailPojo.setUid(userVO.getEmailUid());
        R r = emailFeignService.queryEmailByUid(emailPojo);
        EmailVO queriedEmailInfo = JSONUtils.parseObjFromResult(ConvertObjectUtils.jsonToString(r), "data", EmailVO.class);
        if (queriedEmailInfo == null || queriedEmailInfo.getUid() == null) {
            throw new UserException("查询邮箱失败");
        }
        StorageSendMailInfo sendMailInfo = StorageMailUtils.generateCommonNotice("您的账户已被锁住，需要重新激活", "<a target=\"_blank\" href=\""+ verifyUrl + "\">点击此链接，重新激活账户</a>", queriedEmailInfo.getEmail(), userVO.getUid());
        sendMQMessageService.sendCommonMail(sendMailInfo, AmqpExchangeNameConstant.PPLAX_SEND_MAIL_EXCHANGE,
                "topic", AmqpQueueNameConstant.SEND_HTML_MAIL_ROUTING_KEY, null);
        return true;
    }

}
