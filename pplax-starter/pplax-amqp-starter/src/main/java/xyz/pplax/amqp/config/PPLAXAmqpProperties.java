package xyz.pplax.amqp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;


@Data
@ConfigurationProperties(prefix = PPLAXAmqpProperties.PPLAX_PREFIX)
public class PPLAXAmqpProperties {

    public static final String PPLAX_PREFIX = "pplax.amqp";

    /**
     * 所有交换机的集合
     */
    private List<AmqpExchange> exchanges = new ArrayList<>();

    /**
     * 如果使用rabbitmq发送的消息没有被消息，那么会重新消费，此处配置最大重新消费次数，如果超过这个次数，还没有被消费，那么就
     * 不会被消费
     */
    private Integer amqpMaxRetryConsume;

    /**
     * 默认的过期时间
     */
    private Integer defaultTtl;

    /**
     * 默认的队列最大长度
     */
    private Integer defaultMaxLength;

    @Data
    public static class AmqpExchange {
        /**
         * 普通交换机名称
         */
        private String exchange;

        /**
         * 交换机的类型
         */
        private String exchangeType;

        /**
         * 死信交换机名称
         */
        private String deadLetterExchange;

        /**
         * 死信交换机的类型
         */
        private String deadLetterExchangeType;

        /**
         * 该交换机下得队列
         */
        private List<AmqpQueue> queues = new ArrayList<>();
    }

    @Data
    public static class AmqpQueue {
        /**
         * 普通队列的名称
         */
        private String queue;

        /**
         * 死信队列的名称
         */
        private String deadLetterQueue;

        /**
         * 普通队列的routingKey
         */
        private String routingKey;

        /**
         * 死信队列的routingKey
         */
        private String deadLetterRoutingKey;

        /**
         * 消息的过期时间
         */
        private Integer ttl;

        /**
         * 队列的最大长度
         */
        private Integer maxLength;
    }
}
