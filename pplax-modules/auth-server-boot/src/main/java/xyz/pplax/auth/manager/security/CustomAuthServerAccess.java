package xyz.pplax.auth.manager.security;

import cn.hutool.core.text.AntPathMatcher;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import xyz.pplax.auth.constant.RequestConstant;
import xyz.pplax.auth.util.OauthServerUtils;
import xyz.pplax.core.constant.OpenApiConstant;
import xyz.pplax.core.dto.JwtUserInfo;
import xyz.pplax.core.entity.R;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.starter.util.PPLAXRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义判断逻辑，用在security的access()方法中
 */
@Component
public class CustomAuthServerAccess {

    /**
     * 检查是否有权限
     * @param request
     * @return
     * @throws IOException
     */
    public boolean hasPermission(HttpServletRequest request) throws IOException {

        String whiteUrlStatus = request.getHeader(RequestConstant.REQUEST_WHITE_URL_STATUS);
        configSpringDoc(request);

        // 如果当前路径时白名单，放行
        if ("true".equals(whiteUrlStatus)) {
            return true;
        }

        // 从请求头中获得jwtUserInfo对象
        JwtUserInfo jwtUserInfo = null;
        try {
            jwtUserInfo = PPLAXRequestUtils.getJwtUserInfo(request);
        } catch (Exception e) {
            return authenticationFailure(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }

        // 从请求头中获取token，在网关的全局过滤器中，被加入
        String jwtToken = jwtUserInfo.getJwtToken();

        // 判断jwt是否失效
        boolean tokenExpiration = PPLAXRequestUtils.tokenExpiration(jwtToken);
        if (tokenExpiration) {
            // token过期或者无效 直接抛出异常，auth有自定义的拦截
            return authenticationFailure(ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION);
        }

        // 运行到这里，说明token存在，并且没有失效，不需要验证是否有权限访问，这是网关做的事
        return true;
    }

    /**
     * 响应验证失败信息
     * @param statusCodeEnum
     * @return
     * @throws IOException
     */
    private boolean authenticationFailure(ResponseStatusCodeEnum statusCodeEnum) throws IOException {
        HttpServletResponse response = PPLAXRequestUtils.getCurrentResponse();
        OauthServerUtils.setHeader(response);
        R r = R.failure(statusCodeEnum.getCode(), statusCodeEnum.getMessage());
        OauthServerUtils.writeJson(response, r);
        return false;
    }


    /**
     * 解决springdoc访问时，不能返回接口信息，也就是判断，如果是springdoc的访问，则在RequestContextHolder中添加一个信息
     * 在PPLAXResponseResultHandler中获取是否是springdoc的访问，如果是，则不对响应信息进行处理
     * @param request
     */
    private void configSpringDoc(HttpServletRequest request) {
        // 获得请求uri
        String requestURI = request.getRequestURI();
        final AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 检查是不是springdoc的访问
        if (antPathMatcher.match(OpenApiConstant.SPRING_DOC_REQUEST_PATH, requestURI)) {
            RequestContextHolder.currentRequestAttributes().setAttribute(OpenApiConstant.CONTEXT_REQUEST_HEADER_OF_SPRING_DOC, true, 0);
        }
//        if (OpenApiConstant.SPRING_DOC_REQUEST_PATH.equals(requestURI)) {
//            RequestContextHolder.currentRequestAttributes()
//                    .setAttribute(OpenApiConstant.CONTEXT_REQUEST_HEADER_OF_SPRING_DOC, true, 1);
//        }
    }

}
