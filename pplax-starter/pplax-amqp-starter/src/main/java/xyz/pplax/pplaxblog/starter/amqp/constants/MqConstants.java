package xyz.pplax.pplaxblog.starter.amqp.constants;

public class MqConstants {

    public static final String PPLAX_BLOG = "pplax.blog";
    public static final String PPLAX_LEAVE_MESSAGE = "pplax.leaveMessage";
    public static final String PPLAX_CHAT_MESSAGE = "pplax.chatMessage";
    public static final String PPLAX_COMMENT = "pplax.comment";
    public static final String PPLAX_EMAIL = "pplax.email";
    public static final String PPLAX_SMS = "pplax.sms";
    public static final String PPLAX_REQUEST_LOG = "pplax.requestLog";
    public static final String PPLAX_EXCEPTION_LOG = "pplax.exceptionLog";

    public static final String EXCHANGE_DIRECT = "exchange.direct";

    public static final String ROUTING_KEY_BLOG = "pplax.blog";
    public static final String ROUTING_KEY_LEAVE_MESSAGE = "pplax.leaveMessage";
    public static final String ROUTING_KEY_CHAT_MESSAGE = "pplax.chatMessage";
    public static final String ROUTING_KEY_COMMENT = "pplax.comment";
    public static final String ROUTING_KEY_EMAIL = "pplax.email";
    public static final String ROUTING_KEY_SMS = "pplax.sms";
    public static final String ROUTING_KEY_REQUEST_LOG = "pplax.requestLog";
    public static final String ROUTING_KEY_EXCEPTION_LOG = "pplax.exceptionLog";


    /**
     * RabbitMQ的命令操作
     */
    public final static String COMMAND = "command";
    public final static String EDIT = "edit";
    public final static String ADD = "add";
    public final static String DELETE = "delete";
    public final static String DELETE_BATCH = "deleteBatch";
    public final static String EDIT_BATCH = "editBatch";
    public final static String DELETE_ALL = "deleteAll";

}
