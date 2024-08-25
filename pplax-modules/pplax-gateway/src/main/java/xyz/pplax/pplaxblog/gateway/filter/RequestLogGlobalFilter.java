package xyz.pplax.pplaxblog.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.server.reactive.ServerHttpRequest;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.xo.constants.type.RequestLogTypeConstants;
import xyz.pplax.pplaxblog.xo.entity.RequestLog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个组件用于记录每次请求，存储到t_request_log表中
 */
@Component
public class RequestLogGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 请求开始时间
        long startTime = System.currentTimeMillis();

        // 获取 ServerHttpRequest 对象
        ServerHttpRequest request = exchange.getRequest();

        // 获取请求路径
        String path = request.getURI().getPath();

        // 获取请求方法
        String method = request.getMethodValue();

        RequestLog requestLog = new RequestLog();
        requestLog.setPath(path);
        requestLog.setMethod(method);
        requestLog.setUserUid(getUserUid(request));
        requestLog.setIp(getIpAddress(request));
        requestLog.setAddress(IpUtils.getCityInfo(requestLog.getIp()));
        requestLog.setBrowser(getBrowserInfo(request));
        requestLog.setAccessOs(getOsInfo(request));
        if (path.contains("/api/admin")) {
            requestLog.setType(RequestLogTypeConstants.ADMIN);
        } else {
            requestLog.setType(RequestLogTypeConstants.WEB);
        }

        // 提取请求体
        return request.getBody().collectList().flatMap(dataBuffers -> {
            String requestBody = dataBuffers.stream()
                    .map(buffer -> {
                        try {
                            return StreamUtils.copyToString(buffer.asInputStream(), StandardCharsets.UTF_8);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .reduce("", String::concat);

            // 获得请求参数
            requestLog.setParamsJson(convertRequestParamsToJson(request.getQueryParams(), JSON.parseObject(requestBody)));

            // 重新创建一个ServerHttpRequestDecorator，以便能够重新读取请求体
            ServerHttpRequestDecorator decoratedRequest = new ServerHttpRequestDecorator(request) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return Flux.just(new DefaultDataBufferFactory().wrap(requestBody.getBytes(StandardCharsets.UTF_8)));
                }
            };

            // 更新exchange对象中的request
            return chain.filter(exchange.mutate().request(decoratedRequest).build())
                    .doFinally(signalType -> {
                        // 计算请求耗时
                        long endTime = System.currentTimeMillis();
                        long duration = endTime - startTime;
                        requestLog.setSpendTime(duration);
                        System.out.println("Request processing time: " + duration + " ms");
                        // 交给消息队列
                        rabbitTemplate.convertAndSend(MqConstants.EXCHANGE_DIRECT, MqConstants.PPLAX_REQUEST_LOG, requestLog);
                    });
        });
    }

    @Override
    public int getOrder() {
        return 0; // 过滤器的执行顺序
    }


    /**
     * 获取登录用户uid
     * @param serverHttpRequest
     * @return
     */
    private String getUserUid(ServerHttpRequest serverHttpRequest) {
        String userUid = null;
        List<String> authorization = serverHttpRequest.getHeaders().get("Authorization");
        if (authorization != null && !StringUtils.isEmpty(authorization.get(0))) {
            String accessToken = authorization.get(0).replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            if (payloadByBase64 == null) {
                return null;
            }
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        return userUid;
    }

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public static String getIpAddress(ServerHttpRequest request) {
        // 获取请求头中的 X-Forwarded-For，用于处理通过代理服务器的请求
        String ipAddress = request.getHeaders().getFirst("X-Forwarded-For");

        if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
            // X-Forwarded-For 可能包含多个IP地址，取第一个
            ipAddress = ipAddress.split(",")[0];
        } else {
            // 如果没有通过代理，那么直接获取远程地址
            ipAddress = request.getRemoteAddress().getAddress().getHostAddress();
        }

        return ipAddress;
    }

    /**
     * 获得参数的json串
     * @param queryParams
     * @param bodyParams
     * @return
     */
    private static String convertRequestParamsToJson(Object queryParams, Object bodyParams) {
        // 获取所有查询参数
        Map<String, Object> res = new HashMap<>();
        res.put("queryParams", queryParams);
        res.put("bodyParams", bodyParams);

        // 将参数转换为JSON字符串
        return JSON.toJSONString(res);
    }
    /**
     * 从ServerHttpRequest对象中获取浏览器信息.
     *
     * @param request ServerHttpRequest对象
     * @return 浏览器名称和版本
     */
    private static String getBrowserInfo(ServerHttpRequest request) {
        String userAgentString = request.getHeaders().getFirst("User-Agent");
        if (userAgentString != null) {
            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
            return userAgent.getBrowser().getName() + " " + userAgent.getBrowserVersion();
        }
        return "Unknown Browser";
    }

    /**
     * 从ServerHttpRequest对象中获取操作系统信息.
     *
     * @param request ServerHttpRequest对象
     * @return 操作系统名称
     */
    private static String getOsInfo(ServerHttpRequest request) {
        String userAgentString = request.getHeaders().getFirst("User-Agent");
        if (userAgentString != null) {
            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
            return userAgent.getOperatingSystem().getName();
        }
        return "Unknown OS";
    }
}