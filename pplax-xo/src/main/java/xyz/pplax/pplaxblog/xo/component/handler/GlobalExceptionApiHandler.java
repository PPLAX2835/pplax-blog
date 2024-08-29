package xyz.pplax.pplaxblog.xo.component.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.pplax.pplaxblog.commons.constants.BaseRegexConstants;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.BaseException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.method.HandlerMethod;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.xo.entity.ExceptionLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用 Api Controller 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionApiHandler {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * REST 业务异常
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult restService(Exception e, HandlerMethod handlerMethod, HttpServletRequest httpServletRequest) throws IOException {
        /*
         * 封装异常信息
         */
        String method = httpServletRequest.getMethod();
        String requestURI = httpServletRequest.getHeader("X-Original-Path");
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setUserUid(getUserUid(httpServletRequest));
        exceptionLog.setEndpoint(method + ":" + requestURI);
        exceptionLog.setIp(IpUtils.getIpAddress(httpServletRequest));
        exceptionLog.setAddress(IpUtils.getCityInfo(exceptionLog.getIp()));
        if ("POST".equals(method) && requestURI.startsWith("/api/file")) {
            exceptionLog.setParamsJson("file params");
        } else {
            // 获取所有URL参数
            Map<String, String[]> queryParams = httpServletRequest.getParameterMap();

            // 获取Body参数
            BufferedReader reader = httpServletRequest.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String bodyParams = sb.toString();

            // 封装
            Map<String, Object> res = new HashMap<>();
            res.put("queryParams", queryParams);
            if (bodyParams.startsWith("[")) {
                res.put("bodyParams", JSON.parseArray(bodyParams));
            } else if (bodyParams.startsWith("{")) {
                res.put("bodyParams", JSON.parseObject(bodyParams));
            }
            exceptionLog.setParamsJson(JSON.toJSONString(res));
        }
        exceptionLog.setClassName(e.getStackTrace()[0].getClassName());
        exceptionLog.setMethodName(e.getStackTrace()[0].getMethodName());
        exceptionLog.setExceptionJson(JSON.toJSONString(e));
        exceptionLog.setExceptionMessage(e.getMessage());

        // 提交给消息队列
        rabbitTemplate.convertAndSend(MqConstants.EXCHANGE_DIRECT, MqConstants.PPLAX_EXCEPTION_LOG, exceptionLog);

        /*
         * 获取Controller类名和方法名
         */
        String controllerName = handlerMethod.getBeanType().getName();
        String methodName = handlerMethod.getMethod().getName();

        /*
         * 获取请求的IP地址
         */
        String clientIp = IpUtils.getIpAddress(httpServletRequest);

        log.error("Exception occurred in Controller: {}, Method: {}, Client IP: {}, Error: {}",
                controllerName, methodName, clientIp, e.getMessage());

        /*
         * 业务逻辑异常
         */
        if (e instanceof ApiException) {
            IErrorCode errorCode = ((ApiException) e).getErrorCode();
            if (null != errorCode) {
                log.debug("Rest request error, {}", errorCode);
                return ResponseResult.error(errorCode.toString());
            }
            log.debug("Rest request error, {}", e.getMessage());
            long code = errorCode.getCode();
            return ResponseResult.error((int) code, e.getMessage());
        }

        /*
         * 参数校验异常
         */
        if (e instanceof BindException || e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = null;
            if (e instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
                bindingResult = methodArgumentNotValidException.getBindingResult();
            } else {
                bindingResult = ((BindException) e).getBindingResult();
            }

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
         * Feign异常
         */
        if (e instanceof FeignException) {
            String message = e.getMessage();
            String regex = BaseRegexConstants.JSON_REGEX;

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(message);

            if (matcher.find()) {
                return new ResponseResult(HttpStatus.UNAUTHORIZED, (Object) JSON.parseObject(matcher.group(0)));
            } else {
                return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        /**
         * 自定义异常
         */
        if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            HttpStatus httpStatus = baseException.getHttpStatus();

            log.debug("Rest request error, {}", baseException.getMessage());
            return ResponseResult.error(httpStatus);
        }


        /**
         * 系统内部异常，打印异常栈
         */
        log.error("Error: handleBadRequest StackTrace : %s", e);
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 获取登录用户uid
     * @param httpServletRequest
     * @return
     */
    private String getUserUid(HttpServletRequest httpServletRequest) {
        String userUid = null;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization) && !"undefined".equals(authorization)) {
            String accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        return userUid;
    }
}
