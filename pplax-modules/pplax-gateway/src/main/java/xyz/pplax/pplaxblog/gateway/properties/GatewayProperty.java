package xyz.pplax.pplaxblog.gateway.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "pplax.gateway")
public class GatewayProperty {

    private String[] exclude;       // 白名单配置

}
