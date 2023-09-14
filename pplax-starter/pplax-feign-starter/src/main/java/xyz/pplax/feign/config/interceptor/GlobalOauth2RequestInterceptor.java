package xyz.pplax.feign.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import xyz.pplax.auth.constant.RequestConstant;
import xyz.pplax.feign.config.util.RequestUtils;

import java.util.Map;

@Component
public class GlobalOauth2RequestInterceptor implements RequestInterceptor {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RequestUtils requestUtils;

    @Override
    public void apply(RequestTemplate template) {
        // TODO 暂时解决从消息中间件中调用feign的时候，请求头没有携带的问题
        Map<String, String> requestHeads = requestUtils.getRequestHeadsFromHolder();
        if (requestHeads != null) {
            requestHeads.forEach((key,value) -> {
                if (!"host".equals(key)) {
                    template.header(key, value);
                }
            });
        }

        template.header(RequestConstant.REQUEST_OPEN_FEIGN_HEADER, "true");

        byte[] body = template.body();
        System.out.println();
    }
}