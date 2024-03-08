package xyz.pplax.pplaxblog.commons.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.pplax.pplaxblog.commons.constants.BaseRegexConstants;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.DeleteFailException;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 通用 Api Controller 全局异常处理
 * </p>
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * <p>
     * 自定义 REST 业务异常
     * <p>
     *
     * @param e 异常类型
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult handleBadRequest(Exception e) {
        /*
         * 业务逻辑异常
         */
        if (e instanceof ApiException) {
            IErrorCode errorCode = ((ApiException) e).getErrorCode();
            if (null != errorCode) {
                log.debug("Rest request error, {}", errorCode.toString());
                return ResponseResult.error(errorCode.toString());
            }
            log.debug("Rest request error, {}", e.getMessage());
            return ResponseResult.error(e.getMessage());
        }

        /*
         * 删除异常
         */
        if (e instanceof DeleteFailException) {
            log.debug("Rest request error, {}", e.getMessage());
            return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        /*
         * 参数校验异常
         */
        if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            if (null != bindingResult && bindingResult.hasErrors()) {
                List<Object> jsonList = new ArrayList<Object>();
                bindingResult.getFieldErrors().stream().forEach(fieldError -> {
                    Map<String, Object> jsonObject = new HashMap<String, Object>(2);
                    jsonObject.put("name", fieldError.getField());
                    jsonObject.put("msg", fieldError.getDefaultMessage());
                    jsonList.add(jsonObject);
                });
                return new ResponseResult(HttpStatus.BAD_REQUEST, jsonList);
            }
        }

        /*
         * Feign异常，目前只是针对调用oauth相关接口的非200返回，将来可能还要继续完善
         */
        if (e instanceof FeignException) {
            // 先获取异常信息中包含的json
            String message = e.getMessage();
            String regex = BaseRegexConstants.JSON_REGEX;

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(message);

            if (matcher.find()) {
                return new ResponseResult(HttpStatus.UNAUTHORIZED, matcher.group(0));
            } else {
                return new ResponseResult(HttpStatus.UNAUTHORIZED);
            }
        }


        /**
         * 系统内部异常，打印异常栈
         */
        log.error("Error: handleBadRequest StackTrace : %s", e);
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
