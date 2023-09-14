package xyz.pplax.amqp.comstant;

/**
 * rabbitmq相关枚举
 */
public class AmqpQueueNameConstant {

    /** 发送html邮件的队列及routingKey **/
    public static final String SEND_HTML_MAIL_QUEUE_NAME = "send.html.mail.queue";
    public static final String SEND_HTML_MAIL_ROUTING_KEY = "send.html.amil.routing";

    /** 发送简单文本邮件的队列及routingKey **/
    public static final String SEND_SIMPLE_TEXT_MAIL_QUEUE_NAME = "send.simple.text.mail.queue";
    public static final String SEND_SIMPLE_TEXT_MAIL_ROUTING_KEY = "send.simple.text.mail.routing";

    /** 发送html邮件的死信队列及routingKey **/
    public static final String SEND_HTML_MAIL_DEAD_LETTER_QUEUE_NAME = "send.html.mail.dead.letter.queue";
    public static final String SEND_HTML_MAIL_DEAD_LETTER_ROUTING_KEY = "send.html.mail.dead.letter.routing";

    /** 发送简单文本邮件的死信队列及routingKey **/
    public static final String SEND_SIMPLE_TEXT_MAIL_DEAD_LETTER_QUEUE_NAME = "send.simple.text.mail.dead.letter.queue";
    public static final String SEND_SIMPLE_TEXT_MAIL_DEAD_LETTER_ROUTING_KEY = "send.simple.text.mail.dead.letter.routing";


    /** 生产者生产的消息不合法的队列 **/
    public static final String MISTAKE_MESSAGE_QUEUE = "pplax.mistake.queue";
    /** 生产者生产的消息不合法的routingKey **/
    public static final String MISTAKE_MESSAGE_ROUTING_KEY = "pplax.mistake.routing.key";

    /** 绑定邮箱的队列 **/
    public static final String OPERATE_USER_BINDING_EMAIL_QUEUE = "pplax.operate.user.binding.email.queue";
    /** 绑定邮箱的路由key **/
    public static final String OPERATE_USER_BINDING_EMAIL_ROUTING_KEY = "pplax.operate.user.binding.email.routing";

    /** 绑定邮箱的死信队列 **/
    public static final String DEAD_LETTER_OPERATE_USER_BINDING_EMAIL_QUEUE = "pplax.operate.user.binding.email.dead.letter.queue";
    /** 绑定邮箱的死信队列路由key **/
    public static final String DEAD_LETTER_OPERATE_USER_BINDING_EMAIL_ROUTING_KEY = "pplax.operate.user.binding.email.dead.letter.routing";

    /** 锁住用户账户 **/
    public static final String OPERATE_USER_LOCK_ACCOUNT_QUEUE = "pplax.operate.user.lock.account.queue";
    public static final String OPERATE_USER_LOCK_ACCOUNT_ROUTING_KEY = "pplax.operate.user.lock.account.routing.key";

    /** 更新角色关系的缓存 **/
    public static final String UPDATE_ROLE_PERMISSION_CACHE_QUEUE = "pplax.update.role.permission.queue";
    public static final String UPDATE_ROLE_PERMISSION_CACHE_ROUTING_KEY = "pplax.update.role.permission.routing.key";

    /** 更新角色关系的缓存死信队列 **/
    public static final String  DEAD_LETTER_UPDATE_ROLE_PERMISSION_CACHE_QUEUE = "pplax.update.role.permission.queue";
    public static final String  DEAD_LETTER_UPDATE_ROLE_PERMISSION_CACHE_ROUTING_KEY = "pplax.update.role.permission.routing.key";

    /** 更新白名单的缓存 **/
    public static final String UPDATE_WHITE_URL_CACHE_QUEUE = "pplax.update.white.url.queue";
    public static final String UPDATE_WHITE_URL_CACHE_ROUTING_KEY = "pplax.update.white.url.routing.key";

    /** 更新白名单的缓存死信队列 **/
    public static final String  DEAD_LETTER_UPDATE_WHITE_URL_CACHE_QUEUE = "pplax.update.white.url.queue";
    public static final String  DEAD_LETTER_UPDATE_WHITE_URL_CACHE_ROUTING_KEY = "pplax.update.white.url.routing.key";

    /** 锁住用户账户的死信队列 **/
    public static final String DEAD_LETTER_OPERATE_USER_LOCK_ACCOUNT_QUEUE = "pplax.operate.user.lock.account.dead.letter.queue";
    public static final String DEAD_LETTER_OPERATE_USER_LOCK_ACCOUNT_ROUTING_KEY = "pplax.operate.user.lock.account.dead.letter.routing.key";

    /** 发送文章评论 **/
    public static final String PAGE_COMMENT_QUEUE = "pplax.PAGE.comment.queue";
    public static final String PAGE_COMMENT_ROUTING_KEY = "pplax.PAGE.routingKey";
    public static final String PAGE_COMMENT_DEAD_LETTER_QUEUE = "pplax.PAGE.comment.dead.letter.queue";
    public static final String PAGE_COMMENT_DEAD_LETTER_ROUTING_KEY = "pplax.PAGE.comment.dead.letter.routingKey";
}
