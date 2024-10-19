package xyz.pplax.pplaxblog.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 聚合swagger配置类
 * @author PPLAX
 * @date 2024/8/26
 */
@Primary
@Component
@AllArgsConstructor
public class Swagger2ResourceProvider implements SwaggerResourcesProvider {

    /**
     * swagger默认的url后缀
     */
    private static final String API_URI = "v2/api-docs";
    /**
     * 网关配置项，对应配置文件中配置的spring.cloud.gateway所有子项
     */
    private final GatewayProperties gatewayProperties;
    /**
     * 网关路由
     */
    private final RouteLocator routeLocator;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));

        // 使用正则表达式匹配以"-api-route"结尾的路由ID
        Pattern apiRoutePattern = Pattern.compile(".*-api-route");

        // 遍历配置文件中配置的所有服务
        gatewayProperties.getRoutes().stream()
                // 过滤同名服务，并且ID匹配*-api-route
                .filter(routeDefinition -> routes.contains(routeDefinition.getId())
                        && apiRoutePattern.matcher(routeDefinition.getId()).matches())
                .forEach(route -> route.getPredicates().stream()
                        // 忽略配置文件中断言中配置的Path为空的配置项
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        // 将Path中的路由地址由**改为v2/api-docs，swagger就是通过这个地址来获取接口文档数据的
                        .forEach(predicateDefinition -> resources
                                .add(swaggerResource(route.getId(), predicateDefinition.getArgs()
                                        .get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("**", API_URI)))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
