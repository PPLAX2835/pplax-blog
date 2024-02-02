package xyz.pplax.pplaxblog.commons.response;

import io.swagger.models.auth.In;
import lombok.Getter;

@Getter
public class ResponseCode {

    public static final Integer SUCCESS = 200;

    public static final Integer SUCCESS_HEART = 202;

    public static final Integer ERROR = 400;                  // 请求错误

    public static final Integer NO_AUTH = 401;                // 没有权限

    public static final Integer NOT_FOUND = 404;              // 接口不存在

    public static final Integer TIME_OUT = 408;               // 请求超时

    public static final Integer TOKEN_EXPIRED = 411;          // 登录过期

    public static final Integer USER_ACCOUNT_FREEZE = 411;    // 账号已被冻结

    public static final Integer USER_ACCOUNT_LOGOUT = 411;    // 账号已注销

    public static final Integer DATA_FORMAT_ERROR = 412;      // 数据格式错误

    public static final Integer SQL_INSERT_ERROR = 413;       // 插入数据异常

    public static final Integer SQL_UPDATE_ERROR = 414;       // 更新数据异常

    public static final Integer PHONE_ERROR = 400;            // 手机号码无效

    public static final Integer PHONE_CODE_EXPIRED = 400;     // 验证码无效或过期

    public static final Integer PHONE_CODE_ERROR = 400;       // 验证码错误

    public static final Integer PHONE_HAVE_REGISTERED = 400;  // 手机号码已被注册

    public static final Integer PARAM_ERROR = 400;            // 参数错误

    public static final Integer OPERATION_ERROR = 400;        // 操作过于频繁

    public static final Integer PARAMETRIC_NULL_ANOMALY = 412;// 参数为空异常

    public static final Integer USER_ID_ERROR = 500;          // 用户id缺失异常

    public static final Integer SERVER_ERROR = 500;           // 服务器异常
}