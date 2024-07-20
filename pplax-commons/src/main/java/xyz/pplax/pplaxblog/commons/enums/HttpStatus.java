package xyz.pplax.pplaxblog.commons.enums;

/**
 * 状态码
 */
public enum HttpStatus {

    // ——————————————————————————————
    //            现有规定的状态码
    // ——————————————————————————————
    // 1xx Informational
    CONTINUE(100, "Continue"),
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),
    PROCESSING(102, "Processing"),

    // 2xx Success
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
    NO_CONTENT(204, "No Content"),
    RESET_CONTENT(205, "Reset Content"),
    PARTIAL_CONTENT(206, "Partial Content"),
    MULTI_STATUS(207, "Multi-Status"),
    ALREADY_REPORTED(208, "Already Reported"),
    IM_USED(226, "IM Used"),

    // 3xx Redirection
    MULTIPLE_CHOICES(300, "Multiple Choices"),
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    SEE_OTHER(303, "See Other"),
    NOT_MODIFIED(304, "Not Modified"),
    USE_PROXY(305, "Use Proxy"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    PERMANENT_REDIRECT(308, "Permanent Redirect"),

    // 4xx Client Error
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    PAYMENT_REQUIRED(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
    REQUEST_TIMEOUT(408, "Request Timeout"),
    CONFLICT(409, "Conflict"),
    GONE(410, "Gone"),
    LENGTH_REQUIRED(411, "Length Required"),
    PRECONDITION_FAILED(412, "Precondition Failed"),
    PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
    URI_TOO_LONG(414, "URI Too Long"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    RANGE_NOT_SATISFIABLE(416, "Range Not Satisfiable"),
    EXPECTATION_FAILED(417, "Expectation Failed"),
    MISDIRECTED_REQUEST(421, "Misdirected Request"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    LOCKED(423, "Locked"),
    FAILED_DEPENDENCY(424, "Failed Dependency"),
    UPGRADE_REQUIRED(426, "Upgrade Required"),
    PRECONDITION_REQUIRED(428, "Precondition Required"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),

    // 5xx Server Error
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),
    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),
    INSUFFICIENT_STORAGE(507, "Insufficient Storage"),
    LOOP_DETECTED(508, "Loop Detected"),
    NOT_EXTENDED(510, "Not Extended"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required"),




    //    ——————————————————————————————
    //               自定义状态码
    //    ——————————————————————————————

    // 成功
    INSERT_SUCCESS(200, "插入成功"),
    UPDATE_SUCCESS(200, "更新成功"),
    OPERATION_SUCCESS(200, "操作成功"),
    DELETE_SUCCESS(200, "删除成功"),
    BATCH_DELETE_SUCCESS(200, "批量删除成功"),



    /**
     * curd相关
     */
    // 失败   500000-500099
    OPERATION_FAIL(500001,  "操作失败"),
    INSERT_FAIL(500002, "插入失败"),
    UPDATE_FAIL(500003, "更新失败"),
    DELETE_FAIL(500004, "删除失败"),
    SELECT_FAIL(500005, "查询失败"),

    /**
     * blog表操作相关
     */
    // 失败   500100-500199
    PROMOTE_FAIL(500100, "置顶失败"),
    PROMOTE_CANCEL_FAIL(500100, "取消置顶失败"),

    /**
     * 参数相关
     */
    // 失败    400000-400099
    ENTITY_EXIST(400001, "该实体已存在"),
    REQUIRED_IS_NULL(400002, "必填项不能为空"),
    USERNAME_IS_NULL(400003, "用户名不能为空"),
    ENTITY_NOT_EXIST(400004, "该实体不存在"),
    KEYWORD_IS_NOT_EMPTY(400005, "关键字不能为空"),
    PARAM_INCORRECT(400006, "传入参数有误！"),
    PLEASE_SELECT_A_RECORD_TO_DELETE(400007, "请选择要删除的记录"),
    NOT_IMAGE(400008, "上传的文件不是图片类型"),
    NOT_VIDEO(400008, "上传的文件不是视频类型"),

    /**
     * 拒绝相关
     */
    // 失败   403000-403999
    INTERFACE_FREQUENTLY(4030001, "接口过于频繁调用"),
    ACCESS_DENIED(403002, "访问被拒绝"),
    BLOG_UNDER_THIS_SORT(403003, "该分类下还有博客！"),
    RESOURCE_UNDER_THIS_SORT(403004, "该分类下还有资源！"),
    SUBJECT_UNDER_THIS_SORT(403005, "该专题下还有内容！"),
    PICTURE_UNDER_THIS_SORT(403006, "该分类下还有图片！"),
    BLOG_UNDER_THIS_TAG(403007, "该标签下还有博客！"),
    BLOG_IS_DELETE(403008, "该文章已下架或被删除！"),
    ADMIN_UNDER_THIS_ROLE(403009, "该角色下还有管理员！"),
    DELETE_ADMIN_ACCOUNT_ERROR (403010, "无法删除Admin账号！"),
    UPDATE_ADMIN_PASSWORD_FAILED(403011, "超级管理员admin的密码仅能admin账号操作"),
    CHILDREN_MENU_UNDER_THIS_MENU(403012, "该菜单下还有子菜单！"),
    SERVER_ERROR(403013, "服务器目前繁忙，请稍等片刻后重试"),
    YOU_HAVE_BEEN_PRISE(403014, "您已经点赞过了!"),
    BLOG_UNDER_THIS_USER(403015, "该用户下还有博客"),
    COLLECTION_UNDER_THIS_USER(403016, "该用户下还有收藏"),
    COMMENT_UNDER_THIS_USER(403017, "该用户下还有评论"),
    FEEDBACK_UNDER_THIS_USER(403018, "该用户下还有反馈"),
    FILE_STORAGE_UNDER_THIS_USER(403019, "该用户下还有文件"),
    SAY_UNDER_THIS_USER(403020, "该用户下还有说说"),
    TAG_NAME_EXIST(403021, "该标签名已存在"),
    USERNAME_EXIST(403022, "该用户名已存在"),
    CHILDREN_UNDER_THIS_MENU(403023, "该菜单还存在子菜单"),
    MENU_UNDER_THIS_ROLE(403024, "该角色还拥有权限"),
    INCONSISTENT_PASSWORD_INPUT(403025, "两次密码输入不一致"),
    LAST_PASSWORD_NOT_ALLOWED(403026, "请不要修改上次使用的密码"),

    /**
     * 账户相关
     */
    // 失败   401000-401099
    EMAIL_UNACTIVATED(401001, "邮箱未激活"),
    MOBILE_UNACTIVATED(401002, "手机未激活"),
    ACCOUNT_IS_NOT_REGISTERED(401003, "账户未注册"),
    USER_OR_EMAIL_EXIST(401004, "用户名或邮箱已被注册"),
    ERROR_PASSWORD(401005, "密码错误"),
    LOGIN_ERROR(401006, "用户名或密码错误，错误%d次后，账户将被锁定30分钟"),
    LOGIN_DISABLE(401007, "该账号已被封禁"),
    VALIDATION_CODE_EXPIRED(401008, "验证码已失效"),
    VALIDATION_FAIL(401009, "验证失败，请控制拼图对齐缺口"),
    VALIDATION_CODE_INCORRECT(401010, "验证码不正确"),

    /**
     * 权限相关     40100-40199
     */
    // 失败   401100-401199
    LOGIN_REQUIRED(401101, "需要登陆"),
    NO_ROLE(401102, "没有分配角色权限"),
    INVALID_TOKEN(401103, "token令牌未被识别"),
    EXPIRED_TOKEN(401104, "token令牌已过期"),
    PLEASE_LOGIN_TO_PRISE(401105, "请先登录后才能点赞!"),
    DATA_NO_PRIVILEGE(401106, "该数据无权限访问"),
    RESTAPI_NO_PRIVILEGE(401107, "您无权进行该操作"),
    ACCESS_NO_PRIVILEGE(401108, "该资源无权限访问"),
    LOGIN_TIMEOUT(401109, "您已退出，请重新登录"),

    /**
     * 服务器内部错误相关
     */
    TOKEN_GET_FAILED(500005, "token获取失败"),

    /**
     * 上传，更新，修改，删除，下载文件相关
     */
    FILE_FAIL_UPLOAD(11110, "文件上传失败"),
    FILE_FAIL_UPDATE(11120, "修改文件失败"),
    FILE_FAIL_DELETE(11130, "删除文件失败"),
    FILE_FAIL_DOWNLOAD(11140, "下载文件失败"),
    FILE_FAIL_CREATE(11150, "未能在指定路径下创建新文件或者文件夹"),
    FILE_FAIL_HAD_DELETED(11160, "文件已被删除"),
    FILE_PERMISSION(11200, "没有权限操作文件"),
    FILE_NOT_FOUND(11300, "未发现该文件"),
    FILE_ALREADY_EXIST(11400, "已经存在该文件"),
    FILE_FAIL_PROPERTY(11500, "获取文件属性失败"),
    FILE_EXCEED_MAX_SIZE(11600, "上传文件超过最大上传大小"),

    //    邮件相关异常
    EMAIL_SEND_PASSWORD_MISTAKE(13110, "发件者邮箱密码错误"),
    EMAIL_SEND_HOST_MISTAKE(13120, "发件者邮箱主机错误"),
    EMAIL_SEND_PROTOCOL_MISTAKE(13130, "发件者邮箱协议错误"),
    EMAIL_RECEIVE_SEND_FAILURE(13210, "邮件发送失败"),
    EMAIL_SEND_CONTENT_TO_LONG(13220, "发送的邮件内容太长"),
    EMAIL_EXISTS(13310, "邮箱已存在"),
    EMAIL_MISSING_RECEIVER(13310, "缺失收件人邮箱"),
    EMAIL_NOT_EXISTS(13310, "邮箱未存在"),
    EMAIL_FAIL_BINDING(13410, "邮箱绑定失败"),
    EMAIL_HAD_BINDING(13510, "邮箱已经绑定"),
    EMAIL_MISTAKE(13510, "传入的不是一个邮箱号");


    /**
     * 响应码
     */
    private final Integer code;

    /**
     * 响应码描述
     */
    private final String message;

    HttpStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
