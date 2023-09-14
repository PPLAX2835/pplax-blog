package xyz.pplax.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发生异常的时候，返回的实体
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExceptionResultEntity {

    /**
     * 异常消息
     */
    private String message;

    /**
     * 异常响应码
     */
    private Integer code;

    /**
     * 发生异常的请求路径
     */
    private String errorUrl;

    /**
     * 额外的数据
     */
    private Object data;

    public ExceptionResultEntity(String message, String errorUrl,int code) {
        this.message = message;
        this.errorUrl = errorUrl;
        this.code = code;
    }
}
