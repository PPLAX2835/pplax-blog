package xyz.pplax.pplaxblog.commons.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {

    private Integer code;

    private String message;

    private Object data;

    /**
     * 成功返回无数据
     *
     * @return
     */
    public static ResponseResult success() {
        return new ResponseResult(ResponseCode.SUCCESS);
    }

    /**
     * 成功返回
     *
     * @param data 返回数据
     * @return
     */
    public static ResponseResult success(Object data) {
        return new ResponseResult(ResponseCode.SUCCESS, data);
    }

    /**
     * 成功返回 带有数据、消息
     * @param data
     * @param message
     * @return
     */
    public static ResponseResult success(Object data, String message) {
        return new ResponseResult(ResponseCode.SUCCESS, data, message);
    }

    /**
     * 失败返回
     */
    public static ResponseResult error(Integer responseCode, String errorMessage) {
        return new ResponseResult(responseCode, errorMessage);
    }

    /**
     * 失败返回
     *
     * @param errorMessage 自定义错误信息
     * @return
     */
    public static ResponseResult error(String errorMessage) {
        return new ResponseResult(ResponseCode.ERROR, errorMessage);
    }

    /**
     * 失败返回
     *
     * @return
     */
    public static ResponseResult error(Integer responseCode, String errorMessage, Object data) {
        return new ResponseResult(responseCode, errorMessage, data);
    }

    /**
     * 失败返回
     *
     * @return
     */
    public static ResponseResult error(Integer responseCode, Object data) {
        return new ResponseResult(responseCode, data);
    }

    public ResponseResult(Integer responseCode) {
        this.code = responseCode;
    }

    public ResponseResult(Integer responseCode, Object data) {
        this.code = responseCode;
        this.data = data;
    }


    public ResponseResult(Integer responseCode, Object data, String message) {
        this.code = responseCode;
        this.data = data;
        this.message = message;
    }

    public ResponseResult(Integer responseCode, String message) {
        this.code = responseCode;
        this.message = message;
    }
}