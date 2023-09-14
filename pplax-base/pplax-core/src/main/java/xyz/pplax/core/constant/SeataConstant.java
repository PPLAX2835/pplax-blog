package xyz.pplax.core.constant;

/**
 * seata相关常量
 */
public class SeataConstant {
    /** 如果需要seata进行跨服务传递，那么请求头中存放xid的header名字 **/
    public static final String GLOBAL_XID_REQUEST_HEADER_NAME = "transactionalXid";
}
