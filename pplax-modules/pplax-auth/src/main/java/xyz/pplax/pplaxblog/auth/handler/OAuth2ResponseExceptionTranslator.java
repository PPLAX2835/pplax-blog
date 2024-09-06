package xyz.pplax.pplaxblog.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.BaseException;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.component.handler.GlobalExceptionApiHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 认证 Api Controller 异常处理
 */
@RestControllerAdvice
@Slf4j
public class OAuth2ResponseExceptionTranslator extends GlobalExceptionApiHandler {

    /**
     * REST 业务异常
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult handleException(Exception e, HandlerMethod handlerMethod, HttpServletRequest httpServletRequest) throws IOException {

        // 发送异常信息
        sendToMq(e, handlerMethod, httpServletRequest);

        //对异常进行转换
        if (e instanceof UnsupportedGrantTypeException){
            return ResponseResult.error(HttpStatus.INVALID_TOKEN);
        }
        if (e instanceof InvalidTokenException) {
            return ResponseResult.error(HttpStatus.INVALID_TOKEN);
        }
        // 认证相关异常，包含自定义异常
        if (e instanceof AuthenticationException) {
            return ResponseResult.error(HttpStatus.UNAUTHORIZED.getCode(), e.getMessage());
        }

        if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            return ResponseResult.error(baseException.getHttpStatus());
        }

        /**
         * 系统内部异常，打印异常栈
         */
        log.error("Error: handleBadRequest StackTrace : %s", e);
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}