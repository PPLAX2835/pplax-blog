package xyz.pplax.amqp.comstant;

/**
 * rabbitmq的交换机名称常量
 */
public class AmqpExchangeNameConstant {
    // =============> 发送邮件的交换机
    /** 发送邮件的普通交换机名称 **/
    public static final String PPLAX_SEND_MAIL_EXCHANGE = "pplax.send.email.common.exchange";
    /** 发送邮件的死信交换机名称 **/
    public static final String PPLAX_SEND_MAIL_DEAD_LETTER_EXCHANGE = "pplax.send.email.dead.letter.exchange";


    // =============> 发送错误消息的交换机
    /** 如果生产者生产的消息不合法，那么都会被该交换机接收 **/
    public static final String MISTAKE_MESSAGE_EXCHANGE = "pplax.send.mistake.exchange";


    // =============> 发送操作用户的交换机
    /** 发送操作邮件的交换机 **/
    public static final String PPLAX_SEND_OPERATE_USER_EXCHANGE = "pplax.send.operate.user.exchange";
    /** 发送操作邮件的死信交换机 **/
    public static final String PPLAX_SEND_OPERATE_USER_DEAD_LETTER_EXCHANGE = "pplax.send.operate.user.dead.letter.exchange";


    // =============> 发送评论的交换机
    /** 如果有新评论，则将此评论的信息发送到此交换机中 **/
    public static final String PPLAX_SEND_COMMENT_EXCHANGE = "pplax.send.comment.exchange";
    /** 发送评论的死信交换机 **/
    public static final String PPLAX_SEND_COMMENT_DEAD_LETTER_EXCHANGE = "pplax.send.comment.dead.letter.exchange";
}
