package xyz.pplax.starter.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import xyz.pplax.auth.constant.RequestConstant;
import xyz.pplax.core.dto.JwtUserInfo;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.user.UserException;

/**
 * 通用的和用户相关的工具类
 */
public class UserUtils {

    /**
     * 从RequestContextHolder中获取当前已验证的用户 因为发送
     * @return
     */
    public static JwtUserInfo getCurrentUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        JwtUserInfo jwtUserInfo = null;
        if (requestAttributes != null) {
            jwtUserInfo = (JwtUserInfo) requestAttributes.getAttribute(RequestConstant.REQUEST_STORAGE_JWT_USER_INFO_NAME, 1);
        }

        return jwtUserInfo;
    }

    public static Long getCurrentUserUid() {
        JwtUserInfo currentUser = getCurrentUser();
        // 判断请求路径是否是白名单，如果是白名单，则不做任何处理
        String whiteUrlStatus = (String) RequestContextHolder.currentRequestAttributes().getAttribute(RequestConstant.CONTEXT_WHITE_URL_STATUS, 1);
        if (!"true".equals(whiteUrlStatus) && currentUser == null) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }
        if ("true".equals(whiteUrlStatus)) {
            return null;
        }
        return currentUser.getUserUid();
    }
}
