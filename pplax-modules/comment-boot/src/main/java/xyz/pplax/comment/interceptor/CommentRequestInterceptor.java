package xyz.pplax.comment.interceptor;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.pplax.core.constant.SeataConstant;

/**
 * 在使用feign进行远程服务调用的时候，在这里将seata的xid增加到请求头中去，
 * 然后在目标服务中，获取到此xid，进行全局事务的绑定操作
 */
@Component
public class CommentRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String xid = RootContext.getXID();
        if (StringUtils.hasLength(xid)) {
            template.header(SeataConstant.GLOBAL_XID_REQUEST_HEADER_NAME,xid);
        }
    }
}
