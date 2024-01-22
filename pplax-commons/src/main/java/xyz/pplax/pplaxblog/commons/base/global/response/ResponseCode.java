package xyz.pplax.pplaxblog.commons.base.global.response;

import lombok.Getter;

@Getter
public enum ResponseCode {

    /**
     * 枚举为状态码和错误信息
     */
    SUCCESS(200, null),

    SUCCESS_HEART(202, null),

    ERROR(400, "请求错误"),

    NO_AUTH(401, "没有权限"),

    NOT_FOUND(404, "接口不存在"),

    TIME_OUT(408, "请求超时"),

    TOKEN_EXPIRED(411, "登录过期"),

    USER_ACCOUNT_FREEZE(411, "你的账号已被冻结"),

    USER_ACCOUNT_LOGOUT(411, "你的账号已注销"),

    DATA_FORMAT_ERROR(412, "数据格式错误"),

    SQL_INSERT_ERROR(413, "插入数据异常"),

    SQL_UPDATE_ERROR(414, "更新数据异常"),

    PHONE_ERROR(400, "手机号码无效"),

    PHONE_CODE_EXPIRED(400, "验证码无效或过期"),

    PHONE_CODE_ERROR(400, "验证码错误"),

    PHONE_HAVE_REGISTERED(400, "手机号码已被注册"),

    PARAM_ERROR(400, "参数错误"),

    OPERATION_ERROR(400, "操作过于频繁"),

    PARAMETRIC_NULL_ANOMALY(412, "参数为空异常"),

    USER_ID_ERROR(500, "用户id缺失异常"),

    SERVER_ERROR(500, "服务器异常");


    Integer code;
    String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}