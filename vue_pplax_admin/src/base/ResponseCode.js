// ResponseCode.js
/**
 * 枚举为状态码和错误信息
 */
const ResponseCode = {
    SUCCESS: { code: 200, message: null },
    SUCCESS_HEART: { code: 202, message: null },
    ERROR: { code: 400, message: '请求错误' },
    NO_AUTH: { code: 401, message: '没有权限' },
    NOT_FOUND: { code: 404, message: '接口不存在' },
    TIME_OUT: { code: 408, message: '请求超时' },
    TOKEN_EXPIRED: { code: 411, message: '登录过期' },
    USER_ACCOUNT_FREEZE: { code: 411, message: '你的账号已被冻结' },
    USER_ACCOUNT_LOGOUT: { code: 411, message: '你的账号已注销' },
    DATA_FORMAT_ERROR: { code: 412, message: '数据格式错误' },
    SQL_INSERT_ERROR: { code: 413, message: '插入数据异常' },
    SQL_UPDATE_ERROR: { code: 414, message: '更新数据异常' },
    PHONE_ERROR: { code: 400, message: '手机号码无效' },
    PHONE_CODE_EXPIRED: { code: 400, message: '验证码无效或过期' },
    PHONE_CODE_ERROR: { code: 400, message: '验证码错误' },
    PHONE_HAVE_REGISTERED: { code: 400, message: '手机号码已被注册' },
    PARAM_ERROR: { code: 400, message: '参数错误' },
    OPERATION_ERROR: { code: 400, message: '操作过于频繁' },
    PARAMETRIC_NULL_ANOMALY: { code: 412, message: '参数为空异常' },
    USER_ID_ERROR: { code: 500, message: '用户id缺失异常' },
    SERVER_ERROR: { code: 500, message: '服务器异常' }
  };
  
  export default ResponseCode;
  