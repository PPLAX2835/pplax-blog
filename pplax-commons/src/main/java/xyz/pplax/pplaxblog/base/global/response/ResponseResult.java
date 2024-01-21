package xyz.pplax.pplaxblog.base.global.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {

    private Integer code;

    private String message;

    private T data;

    /**
     * 成功返回无数据
     *
     * @return
     */
    public static <V> ResponseResult<V> success() {
        return new ResponseResult<>(ResponseCode.SUCCESS.code, ResponseCode.SUCCESS.message);
    }

    /**
     * 成功返回
     *
     * @param data 返回数据
     * @return
     */
    public static <V> ResponseResult<V> success(V data) {
        return new ResponseResult<>(ResponseCode.SUCCESS.code, ResponseCode.SUCCESS.message, data);
    }

    /**
     * 失败返回
     *
     * @param errorInfo 错误信息
     * @return
     */
    public static <V> ResponseResult<V> error(ResponseCode errorInfo) {
        return new ResponseResult<>(errorInfo.code, errorInfo.message);
    }

    /**
     * 失败返回
     *
     * @param str 自定义错误信息
     * @return
     */
    public static <V> ResponseResult<V> error(String str) {
        return new ResponseResult<>(ResponseCode.ERROR.code, str);
    }

    /**
     * 失败返回
     *
     * @return
     */
    public static <V> ResponseResult<V> error(Integer code, String message, V data) {
        return new ResponseResult<>(code, message, data);
    }

    /**
     * 失败返回
     *
     * @return
     */
    public static <V> ResponseResult<V> error(ResponseCode responseCode, V data) {
        return new ResponseResult<>(responseCode.code, responseCode.message, data);
    }

    public ResponseResult(ResponseCode responseCode, T data) {
        this.code = responseCode.code;
        this.message = responseCode.message;
        this.data = data;
    }

    public ResponseResult(T data) {
        ResponseCode success = ResponseCode.SUCCESS;
        this.code = success.code;
        this.message = success.message;
        this.data = data;
    }

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(ResponseCode responseCode) {
        this.code = responseCode.code;
        this.message = responseCode.message;
    }

    public ResponseResult(ResponseCode responseCode, String message) {
        this.code = responseCode.code;
        this.message = message;
    }
}