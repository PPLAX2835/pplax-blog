package xyz.pplax.pplaxblog.commons.handler;

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
    public R<Object> handleBadRequest(Exception e) {
        /*
         * 业务逻辑异常
         */
        if (e instanceof ApiException) {
            IErrorCode errorCode = ((ApiException) e).getErrorCode();
            if (null != errorCode) {
                log.debug("Rest request error, {}", errorCode.toString());
                return R.failed(errorCode);
            }
            log.debug("Rest request error, {}", e.getMessage());
            return R.failed(e.getMessage());
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
                return R.restResult(jsonList, ApiErrorCode.FAILED);
            }
        }

        /*
         * 登录认证异常
         */
        if (e instanceof FeignException) {
            // 先获取异常信息中包含的json
            String message = e.getMessage();
            String regex = BaseRegexConstants.JSON_REGEX;

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(message);

            if (matcher.find()) {

                return R.restResult(matcher.group(0), ApiErrorCode.FAILED);
            } else {
                return R.failed(ApiErrorCode.FAILED);
            }
        }


        /**
         * 系统内部异常，打印异常栈
         */
        log.error("Error: handleBadRequest StackTrace : {}", e);
        return R.failed(ApiErrorCode.FAILED);
    }
}
