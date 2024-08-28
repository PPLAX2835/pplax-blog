package xyz.pplax.pplaxblog.starter.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;

/**
 * RabbitMQ配置文件【可用于自动生成队列和交换机】
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 声明交换机
     */
    @Bean(MqConstants.EXCHANGE_DIRECT)
    public Exchange EXCHANGE_DIRECT() {
        // 声明路由交换机，durable:在rabbitmq重启后，交换机还在
        return ExchangeBuilder.directExchange(MqConstants.EXCHANGE_DIRECT).durable(true).build();
    }

    /**
     * 声明Blog队列
     *
     * @return
     */
    @Bean(MqConstants.PPLAX_BLOG)
    public Queue PPLAX_BLOG() {
        return new Queue(MqConstants.PPLAX_BLOG);
    }

    /**
     * 声明leaveMessage队列
     *
     * @return
     */
    @Bean(MqConstants.PPLAX_LEAVE_MESSAGE)
    public Queue PPLAX_LEAVE_MESSAGE() {
        return new Queue(MqConstants.PPLAX_LEAVE_MESSAGE);
    }

    /**
     * 声明chatMessage队列
     *
     * @return
     */
    @Bean(MqConstants.PPLAX_CHAT_MESSAGE)
    public Queue PPLAX_CHAT_MESSAGE() {
        return new Queue(MqConstants.PPLAX_CHAT_MESSAGE);
    }

    /**
     * 声明comment队列
     *
     * @return
     */
    @Bean(MqConstants.PPLAX_COMMENT)
    public Queue PPLAX_COMMENT() {
        return new Queue(MqConstants.PPLAX_COMMENT);
    }

    /**
     * 声明Email队列
     *
     * @return
     */
    @Bean(MqConstants.PPLAX_EMAIL)
    public Queue PPLAX_EMAIL() {
        return new Queue(MqConstants.PPLAX_EMAIL);
    }

    /**
     * 声明SMS队列
     *
     * @return
     */
    @Bean(MqConstants.PPLAX_SMS)
    public Queue PPLAX_SMS() {
        return new Queue(MqConstants.PPLAX_SMS);
    }

    /**
     * 声明REQUEST_LOG队列
     *
     * @return
     */
    @Bean(MqConstants.PPLAX_REQUEST_LOG)
    public Queue PPLAX_REQUEST_LOG() {
        return new Queue(MqConstants.PPLAX_REQUEST_LOG);
    }

    /**
     * 声明EXCEPTION_LOG队列
     *
     * @return
     */
    @Bean(MqConstants.PPLAX_EXCEPTION_LOG)
    public Queue PPLAX_EXCEPTION_LOG() {
        return new Queue(MqConstants.PPLAX_EXCEPTION_LOG);
    }

    /**
     * pplax.blog 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_BLOG(@Qualifier(MqConstants.PPLAX_BLOG) Queue queue, @Qualifier(MqConstants.EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MqConstants.ROUTING_KEY_BLOG).noargs();
    }

    /**
     * pplax.leaveMessage 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_LEAVE_MESSAGE(@Qualifier(MqConstants.PPLAX_LEAVE_MESSAGE) Queue queue, @Qualifier(MqConstants.EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MqConstants.ROUTING_KEY_LEAVE_MESSAGE).noargs();
    }

    /**
     * pplax.chatMessage 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_CHAT_MESSAGE(@Qualifier(MqConstants.PPLAX_CHAT_MESSAGE) Queue queue, @Qualifier(MqConstants.EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MqConstants.ROUTING_KEY_CHAT_MESSAGE).noargs();
    }

    /**
     * pplax.comment 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_COMMENT(@Qualifier(MqConstants.PPLAX_COMMENT) Queue queue, @Qualifier(MqConstants.EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MqConstants.ROUTING_KEY_COMMENT).noargs();
    }
    /**
     * pplax.mail 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(MqConstants.PPLAX_EMAIL) Queue queue, @Qualifier(MqConstants.EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MqConstants.ROUTING_KEY_EMAIL).noargs();
    }

    /**
     * pplax.sms 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(MqConstants.PPLAX_SMS) Queue queue, @Qualifier(MqConstants.EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MqConstants.ROUTING_KEY_SMS).noargs();
    }

    /**
     * pplax.requestLog 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_REQUEST_LOG(@Qualifier(MqConstants.PPLAX_REQUEST_LOG) Queue queue, @Qualifier(MqConstants.EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MqConstants.ROUTING_KEY_REQUEST_LOG).noargs();
    }

    /**
     * pplax.exceptionLog 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_EXCEPTION_LOG(@Qualifier(MqConstants.PPLAX_EXCEPTION_LOG) Queue queue, @Qualifier(MqConstants.EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MqConstants.ROUTING_KEY_EXCEPTION_LOG).noargs();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
