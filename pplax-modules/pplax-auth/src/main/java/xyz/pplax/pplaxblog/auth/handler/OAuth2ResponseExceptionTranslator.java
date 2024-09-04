package xyz.pplax.pplaxblog.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;

/**
 * 认证 Api Controller 异常处理
 */
@Slf4j
@Configuration
public class OAuth2ResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<ResponseResult> translate(Exception e) throws Exception {
        log.error("认证服务器认证异常：{}", e.getMessage());
        //对异常进行转换
        if (e instanceof UnsupportedGrantTypeException){
            return ResponseEntity.status(HttpStatus.INVALID_TOKEN.getCode()).contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseResult.error(HttpStatus.INVALID_TOKEN));
        }
        if (e instanceof InvalidTokenException) {
            return ResponseEntity.status(HttpStatus.INVALID_TOKEN.getCode()).contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseResult.error(HttpStatus.INVALID_TOKEN));
        }
        // 认证相关异常，包含自定义异常
        if (e instanceof AuthenticationException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.getCode()).contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseResult.error(HttpStatus.UNAUTHORIZED.getCode(), e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.getCode()).contentType(MediaType.APPLICATION_JSON)
                .body(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}