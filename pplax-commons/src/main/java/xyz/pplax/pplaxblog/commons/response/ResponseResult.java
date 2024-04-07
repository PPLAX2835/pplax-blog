package xyz.pplax.pplaxblog.commons.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {

    @ApiModelProperty(value = "响应码", required = false)
    private Integer code;

    @ApiModelProperty(value = "消息", required = false)
    private String message;

    @ApiModelProperty(value = "响应数据", required = false)
    private Object data;

    @ApiModelProperty(value = "响应数据", required = false)
    private Map<String,Object> extra = new HashMap<>();

    @ApiModelProperty(value = "响应数据总数", required = false)
    private Long total;

    /**
     * 成功返回无数据
     *
     * @return
     */
    public static ResponseResult success() {
        return new ResponseResult(HttpStatus.OK.getCode());
    }

    /**
     * 成功返回
     *
     * @param data 返回数据
     * @return
     */
    public static ResponseResult success(Object data) {
        return new ResponseResult(HttpStatus.OK.getCode(), data);
    }

    public static ResponseResult success(Object data, Long total) {
        return new ResponseResult(HttpStatus.OK, data, total);
    }

    public static ResponseResult success(Map<String,Object> extra) {
        return new ResponseResult(HttpStatus.OK, extra);
    }

    public static ResponseResult success(Object data, Map<String,Object> extra) {
        return new ResponseResult(HttpStatus.OK, data, extra);
    }

    /**
     * 成功返回 带有数据、消息
     * @param data
     * @param message
     * @return
     */
    public static ResponseResult success(Object data, String message) {
        return new ResponseResult(HttpStatus.OK.getCode(), data, message);
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
     * @return
     */
    public static ResponseResult error(Integer responseCode, Object data) {
        return new ResponseResult(responseCode, data);
    }

    /**
     * 失败返回
     *
     * @param httpStatus
     * @return
     */
    public static ResponseResult error(HttpStatus httpStatus) {
        return new ResponseResult(httpStatus.getCode(), httpStatus.getMessage());
    }

    /**
     * 失败返回
     *
     * @param message
     * @return
     */
    public static ResponseResult error(String message) {
        return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), message);
    }





    public ResponseResult(HttpStatus httpStatus) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
    }

    public ResponseResult(HttpStatus httpStatus, Object data) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
        this.data = data;
    }

    public ResponseResult(HttpStatus httpStatus, Map<String, Object> extra) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
        this.extra = extra;
    }

    public ResponseResult(HttpStatus httpStatus, Object data, Map<String, Object> extra) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
        this.data = data;
        this.extra = extra;
    }

    public ResponseResult(HttpStatus httpStatus, Object data, Long total) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
        this.data = data;
        this.total = total;
    }

    public ResponseResult(Integer responseCode) {
        this.code = responseCode;
    }

    public ResponseResult(Integer responseCode, String message) {
        this.code = responseCode;
        this.message = message;
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

}