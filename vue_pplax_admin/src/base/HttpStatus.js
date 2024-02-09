// HttpStatus.js
/**
 * 枚举为状态码和信息
 */
const HttpStatus = {

    // ——————————————————————————————
    //            现有规定的状态码
    // ——————————————————————————————

    // 2xx Success
    OK: { code: 200, message: 'OK' },
    CREATED: { code: 201, message: 'Created' },
    ACCEPTED: { code: 202, message: 'Accepted' },
    NON_AUTHORITATIVE_INFORMATION: { code: 203, message: 'Non-Authoritative Information' },
    NO_CONTENT: { code: 204, message: 'No Content' },
    RESET_CONTENT: { code: 205, message: 'Reset Content' },
    PARTIAL_CONTENT: { code: 206, message: 'Partial Content' },
    MULTI_STATUS: { code: 207, message: 'Multi-Status' },
    ALREADY_REPORTED: { code: 208, message: 'Already Reported' },
    IM_USED: { code: 226, message: 'IM Used' },

    // 3xx Redirection
    MULTIPLE_CHOICES: { code: 300, message: 'Multiple Choices' },
    MOVED_PERMANENTLY: { code: 301, message: 'Moved Permanently' },
    FOUND: { code: 302, message: 'Found' },
    SEE_OTHER: { code: 303, message: 'See Other' },
    NOT_MODIFIED: { code: 304, message: 'Not Modified' },
    USE_PROXY: { code: 305, message: 'Use Proxy' },
    TEMPORARY_REDIRECT: { code: 307, message: 'Temporary Redirect' },
    PERMANENT_REDIRECT: { code: 308, message: 'Permanent Redirect' },

    // 4xx Client Error
    BAD_REQUEST: { code: 400, message: 'Bad Request' },
    UNAUTHORIZED: { code: 401, message: 'Unauthorized' },
    PAYMENT_REQUIRED: { code: 402, message: 'Payment Required' },
    FORBIDDEN: { code: 403, message: 'Forbidden' },
    NOT_FOUND: { code: 404, message: 'Not Found' },
    METHOD_NOT_ALLOWED: { code: 405, message: 'Method Not Allowed' },
    NOT_ACCEPTABLE: { code: 406, message: 'Not Acceptable' },
    PROXY_AUTHENTICATION_REQUIRED: { code: 407, message: 'Proxy Authentication Required' },
    REQUEST_TIMEOUT: { code: 408, message: 'Request Timeout' },
    CONFLICT: { code: 409, message: 'Conflict' },
    GONE: { code: 410, message: 'Gone' },
    LENGTH_REQUIRED: { code: 411, message: 'Length Required' },
    PRECONDITION_FAILED: { code: 412, message: 'Precondition Failed' },
    PAYLOAD_TOO_LARGE: { code: 413, message: 'Payload Too Large' },
    URI_TOO_LONG: { code: 414, message: 'URI Too Long' },
    UNSUPPORTED_MEDIA_TYPE: { code: 415, message: 'Unsupported Media Type' },
    RANGE_NOT_SATISFIABLE: { code: 416, message: 'Range Not Satisfiable' },
    EXPECTATION_FAILED: { code: 417, message: 'Expectation Failed' },
    MISDIRECTED_REQUEST: { code: 421, message: 'Misdirected Request' },
    UNPROCESSABLE_ENTITY: { code: 422, message: 'Unprocessable Entity' },
    LOCKED: { code: 423, message: 'Locked' },
    FAILED_DEPENDENCY: { code: 424, message: 'Failed Dependency' },
    UPGRADE_REQUIRED: { code: 426, message: 'Upgrade Required' },
    PRECONDITION_REQUIRED: { code: 428, message: 'Precondition Required' },
    TOO_MANY_REQUESTS: { code: 429, message: 'Too Many Requests' },
    REQUEST_HEADER_FIELDS_TOO_LARGE: { code: 431, message: 'Request Header Fields Too Large' },
    UNAVAILABLE_FOR_LEGAL_REASONS: { code: 451, message: 'Unavailable For Legal Reasons' },

    // 5xx Server Error
    INTERNAL_SERVER_ERROR: { code: 500, message: 'Internal Server Error' },
    NOT_IMPLEMENTED: { code: 501, message: 'Not Implemented' },
    BAD_GATEWAY: { code: 502, message: 'Bad Gateway' },
    SERVICE_UNAVAILABLE: { code: 503, message: 'Service Unavailable' },
    GATEWAY_TIMEOUT: { code: 504, message: 'Gateway Timeout' },
    HTTP_VERSION_NOT_SUPPORTED: { code: 505, message: 'HTTP Version Not Supported' },
    VARIANT_ALSO_NEGOTIATES: { code: 506, message: 'Variant Also Negotiates' },
    INSUFFICIENT_STORAGE: { code: 507, message: 'Insufficient Storage' },
    LOOP_DETECTED: { code: 508, message: 'Loop Detected' },
    NOT_EXTENDED: { code: 510, message: 'Not Extended' },
    NETWORK_AUTHENTICATION_REQUIRED: { code: 511, message: 'Network Authentication Required' },


    //    ——————————————————————————————
    //               自定义状态码
    //    ——————————————————————————————
    
    /**
     * curd相关
     */
    // 成功   200000-200099
    INSERT_SUCCESS: { code: 200001, message: '插入成功' },
    UPDATE_SUCCESS: { code: 200002, message: '更新成功' },
    OPERATION_SUCCESS: { code: 200003, message: '操作成功' },
    DELETE_SUCCESS: { code: 200004, message: '删除成功' },
    BATCH_DELETE_SUCCESS: { code: 200005, message: '批量删除成功' },
    // 失败   500000-500099
    OPERATION_FAIL: { code: 500001, message: '操作失败' },
    INSERT_FAIL: { code: 500002, message: '插入失败' },
    UPDATE_FAIL: { code: 500003, message: '更新失败' },
    DELETE_FAIL: { code: 500004, message: '删除失败' },


    /**
     * 参数相关
     */
    // 成功   200100-200199

    // 失败    400000-400099
    ENTITY_EXIST: { code: 400001, message: '该实体已存在' },
    REQUIRED_IS_NULL: { code: 400002, message: '必填项不能为空' },
    USERNAME_IS_NULL: { code: 400003, message: '用户名不能为空' },
    ENTITY_NOT_EXIST: { code: 400004, message: '该实体不存在' },
    KEYWORD_IS_NOT_EMPTY: { code: 400005, message: '关键字不能为空' },
    PARAM_INCORRECT: { code: 400006, message: '传入参数有误！' },
    PLEASE_SELECT_A_RECORD_TO_DELETE: { code: 400007, message: '请选择要删除的记录' },


    
    /**
     * 拒绝相关
     */

    // 失败   403000-403999
    INTERFACE_FREQUENTLY: { code: 4030001, message: '接口过于频繁调用' },
    ACCESS_DENIED: { code: 403002, message: '访问被拒绝' },
    BLOG_UNDER_THIS_SORT: { code: 403003, message: '该分类下还有博客！' },
    RESOURCE_UNDER_THIS_SORT: { code: 403004, message: '该分类下还有资源！' },
    SUBJECT_UNDER_THIS_SORT: { code: 403005, message: '该专题下还有内容！' },
    PICTURE_UNDER_THIS_SORT: { code: 403006, message: '该分类下还有图片！' },
    BLOG_UNDER_THIS_TAG: { code: 403007, message: '该标签下还有博客！' },
    BLOG_IS_DELETE: { code: 403008, message: '该文章已下架或被删除！' },
    ADMIN_UNDER_THIS_ROLE: { code: 403009, message: '该角色下还有管理员！' },
    DELETE_ADMIN_ACCOUNT_ERROR: { code: 403010, message: '无法删除Admin账号！' },
    UPDATE_ADMIN_PASSWORD_FAILED: { code: 403011, message: '超级管理员admin的密码仅能admin账号操作' },
    CHILDREN_MENU_UNDER_THIS_MENU: { code: 403012, message: '该菜单下还有子菜单！' },
    SERVER_ERROR: { code: 403013, message: '服务器目前繁忙，请稍等片刻后重试' },
    YOU_HAVE_BEEN_PRISE: { code: 403014, message: '您已经点赞过了!' },


    /**
     * 账户相关
     */
    // 成功   200200-200299

    // 失败   401000-401099
    EMAIL_UNACTIVATED: { code: 401001, message: '邮箱未激活' },
    MOBILE_UNACTIVATED: { code: 401002, message: '手机未激活' },
    ACCOUNT_IS_NOT_REGISTERED: { code: 401003, message: '账户未注册' },
    USER_OR_EMAIL_EXIST: { code: 401004, message: '用户名或邮箱已被注册' },
    ERROR_PASSWORD: { code: 401005, message: '密码错误' },
    LOGIN_ERROR: { code: 401006, message: '用户名或密码错误，错误%d次后，账户将被锁定30分钟' },
    LOGIN_DISABLE: { code: 401007, message: '该账号已被封禁' },


    
    //    邮件相关异常
    EMAIL_SEND_PASSWORD_MISTAKE: { code: 13110, message: '发件者邮箱密码错误' },
    EMAIL_SEND_HOST_MISTAKE: { code: 13120, message: '发件者邮箱主机错误' },
    EMAIL_SEND_PROTOCOL_MISTAKE: { code: 13130, message: '发件者邮箱协议错误' },
    EMAIL_RECEIVE_SEND_FAILURE: { code: 13210, message: '邮件发送失败' },
    EMAIL_SEND_CONTENT_TO_LONG: { code: 13220, message: '发送的邮件内容太长' },
    EMAIL_EXISTS: { code: 13310, message: '邮箱已存在' },
    EMAIL_MISSING_RECEIVER: { code: 13310, message: '缺失收件人邮箱' },
    EMAIL_NOT_EXISTS: { code: 13310, message: '邮箱未存在' },
    EMAIL_FAIL_BINDING: { code: 13410, message: '邮箱绑定失败' },
    EMAIL_HAD_BINDING: { code: 13510, message: '邮箱已经绑定' },
    EMAIL_MISTAKE: { code: 13510, message: '传入的不是一个邮箱号' },


    /**
     * 权限相关     41000-41999
     */

    // 成功   200300-200399

    // 失败   401100-401199
    LOGIN_REQUIRED: { code: 401101, message: '需要登陆' },
    NO_ROLE: { code: 401102, message: '没有分配角色权限' },
    INVALID_TOKEN: { code: 401103, message: 'token令牌未被识别' },
    EXPIRED_TOKEN: { code: 401104, message: 'token令牌已过期' },
    PLEASE_LOGIN_TO_PRISE: { code: 401105, message: '请先登录后才能点赞!' },
    DATA_NO_PRIVILEGE: { code: 401106, message: '该数据无权限访问' },
    RESTAPI_NO_PRIVILEGE: { code: 401107, message: '您无权进行该操作' },
    ACCESS_NO_PRIVILEGE: { code: 401108, message: '该资源无权限访问' },
    LOGIN_TIMEOUT: { code: 401109, message: '您已退出，请重新登录' },


    /**
     * 上传，更新，修改，删除，下载文件相关
     */
    FILE_FAIL_UPLOAD: { code: 11110, message: '文件上传失败' },
    FILE_FAIL_UPDATE: { code: 11120, message: '修改文件失败' },
    FILE_FAIL_DELETE: { code: 11130, message: '删除文件失败' },
    FILE_FAIL_DOWNLOAD: { code: 11140, message: '下载文件失败' },
    FILE_FAIL_CREATE: { code: 11150, message: '未能在指定路径下创建新文件或者文件夹' },
    FILE_FAIL_HAD_DELETED: { code: 11160, message: '文件已被删除' },
    FILE_PERMISSION: { code: 11200, message: '没有权限操作文件' },
    FILE_NOT_FOUND: { code: 11300, message: '未发现该文件' },
    FILE_ALREADY_EXIST: { code: 11400, message: '已经存在该文件' },
    FILE_FAIL_PROPERTY: { code: 11500, message: '获取文件属性失败' },
    FILE_EXCEED_MAX_SIZE: { code: 11600, message: '上传文件超过最大上传大小' },
  };
  
  export default HttpStatus;
  