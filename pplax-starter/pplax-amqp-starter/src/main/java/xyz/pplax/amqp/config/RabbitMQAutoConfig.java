package xyz.pplax.amqp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties({PPLAXAmqpProperties.class})
@Configuration
public class RabbitMQAutoConfig {
}
