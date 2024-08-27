package xyz.pplax.pplaxblog.gateway.filter;

import com.alibaba.fastjson.JSON;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
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
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.xo.constants.type.RequestLogTypeConstants;
import xyz.pplax.pplaxblog.xo.entity.RequestLog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 这个组件用于记录每次请求，存储到t_request_log表中
 */
@Slf4j
@Component
public class RequestLogGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        String method = request.getMethodValue();

        RequestLog requestLog = createRequestLog(request, path, method);

        if ("POST".equals(method) && path.startsWith("/api/file")) {
            return chain.filter(exchange)
                    .doFinally(signalType -> logRequest(requestLog, startTime, "file params"));
        }

        return request.getBody().collectList().flatMap(dataBuffers -> {
            String requestBody = dataBuffers.stream()
                    .map(this::bufferToString)
                    .reduce("", String::concat);

            if (requestBody.startsWith("[")) {
                requestLog.setParamsJson(convertRequestParamsToJson(request.getQueryParams(), JSON.parseArray(requestBody)));
            } else if (requestBody.startsWith("{")) {
                requestLog.setParamsJson(convertRequestParamsToJson(request.getQueryParams(), JSON.parseObject(requestBody)));
            }

            ServerHttpRequestDecorator decoratedRequest = new ServerHttpRequestDecorator(request) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return Flux.just(new DefaultDataBufferFactory().wrap(requestBody.getBytes(StandardCharsets.UTF_8)));
                }
            };

            return chain.filter(exchange.mutate().request(decoratedRequest).build())
                    .doFinally(signalType -> logRequest(requestLog, startTime, requestLog.getParamsJson()));
        });
    }

    private void logRequest(RequestLog requestLog, long startTime, String paramsJson) {
        long duration = System.currentTimeMillis() - startTime;
        requestLog.setSpendTime(duration);
        requestLog.setParamsJson(paramsJson);
        log.info("Request processing time: {} ms", duration);
        rabbitTemplate.convertAndSend(MqConstants.EXCHANGE_DIRECT, MqConstants.PPLAX_REQUEST_LOG, requestLog);
    }

    private String bufferToString(DataBuffer buffer) {
        try {
            return StreamUtils.copyToString(buffer.asInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Error reading request body", e);
            return "";
        }
    }

    private RequestLog createRequestLog(ServerHttpRequest request, String path, String method) {
        RequestLog requestLog = new RequestLog();
        requestLog.setEndpoint(method + ":" + path);
        requestLog.setUserUid(getUserUid(request));
        requestLog.setIp(getIpAddress(request));
        requestLog.setAddress(IpUtils.getCityInfo(requestLog.getIp()));
        requestLog.setBrowser(getBrowserInfo(request));
        requestLog.setAccessOs(getOsInfo(request));
        requestLog.setType(path.startsWith("/api/admin") ? RequestLogTypeConstants.ADMIN : RequestLogTypeConstants.WEB);
        return requestLog;
    }

    @Override
    public int getOrder() {
        return 0; // 过滤器的执行顺序
    }

    private String getUserUid(ServerHttpRequest serverHttpRequest) {
        return Optional.ofNullable(serverHttpRequest.getHeaders().get("Authorization"))
                .filter(authHeaders -> !authHeaders.isEmpty())
                .map(authHeaders -> authHeaders.get(0).replace("Bearer ", ""))
                .map(JwtUtil::getPayloadByBase64)
                .map(payload -> JSON.parseObject(payload).getString("uid"))
                .orElse(null);
    }

    public static String getIpAddress(ServerHttpRequest request) {
        return Optional.ofNullable(request.getHeaders().getFirst("X-Forwarded-For"))
                .filter(ip -> !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip))
                .map(ip -> ip.split(",")[0])
                .orElseGet(() -> request.getRemoteAddress().getAddress().getHostAddress());
    }

    private static String convertRequestParamsToJson(Object queryParams, Object bodyParams) {
        Map<String, Object> res = new HashMap<>();
        res.put("queryParams", queryParams);
        res.put("bodyParams", bodyParams);
        return JSON.toJSONString(res);
    }

    private static String getBrowserInfo(ServerHttpRequest request) {
        return Optional.ofNullable(request.getHeaders().getFirst("User-Agent"))
                .map(UserAgent::parseUserAgentString)
                .map(userAgent -> userAgent.getBrowser().getName() + " " + userAgent.getBrowserVersion())
                .orElse("Unknown Browser");
    }

    private static String getOsInfo(ServerHttpRequest request) {
        return Optional.ofNullable(request.getHeaders().getFirst("User-Agent"))
                .map(UserAgent::parseUserAgentString)
                .map(userAgent -> userAgent.getOperatingSystem().getName())
                .orElse("Unknown OS");
    }
}
