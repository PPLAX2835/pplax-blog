package xyz.pplax.article.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.pplax.starter.interceptor.PPLAXGlobalHandlerInterceptor;


/**
 * web配置
 */

@Configuration
public class ArticleWebConfig implements WebMvcConfigurer {

    @Autowired
    private PPLAXGlobalHandlerInterceptor pplaxGlobalHandlerInterceptor;

    /**
     * 增加自定义拦截器
     * @param
     */
    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        //"*/css/**","*/js/**","*/images/**","*/fonts/**"
        registry.addInterceptor(pplaxGlobalHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/","/css/**","/js/**","/images/**","/fonts/**");
    }
}
