package com.timeless.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
* @Description： RabbitMQConfig类中的@Bean注解用于将队列、交换机、绑定关系等RabbitMQ相关的组件注册为Spring的bean，
 *              从而实现这些组件的自动化配置和管理。只有将这些组件注册为bean，才能够在应用程序中使用它们，
 *              并且能够在RabbitMQ中创建对应的队列、交换机等资源。如果没有将这些组件注册为bean，
 *              那么在应用程序中是无法使用它们的，也不可能在RabbitMQ中创建对应的队列、交换机等资源。
 *              因此，将队列、交换机等组件注册为bean是使用RabbitMQ的必要步骤之一。
* @Date: 2023/5/29 19:54
* @Author: timeless
*/
@Configuration
public class RabbitMQConfig {

    public static final String REFUND_QUEUE = "refund_queue";
    public static final String REFUND_ERROR_QUEUE = "refund_error_queue";


    /**
     * ttl交换机
     */
    public static final String TTL_EXCHANGE = "springboot_ttl_exchange";
    /**
     * ttl队列
     */
    private static final String TTL_QUEUE = "springboot_ttl_queue";
    /**
     * 死信交换机
     */
    public static final String DLX_EXCHANGE = "springboot_dlx_exchange";
    /**
     * 死信队列
     */
    private static final String DLX_QUEUE = "springboot_dlx_queue";

    /**
     * 声明ttl交换机
     *
     * @return ttl交换机
     */
    @Bean
    public Exchange ttlExchange() {
        return ExchangeBuilder.topicExchange(TTL_EXCHANGE).build();
    }

    /**
     * 声明ttl队列，同时绑定死信交换机
     * <p>
     * <p>
     * 这里使用了RabbitMQ的TTL（Time To Live）和DLX（Dead Letter Exchange）功能来实现延时队列和自动取消订单的功能。
     * 当你发送一个订单信息到ttl队列时，队列会设置一个过期时间，这里是60秒（即1分钟），
     * 当消息在队列中存活时间超过60秒后，RabbitMQ会将该消息发送到绑定的DLX交换机上，
     * 并且将消息路由到绑定的DLX队列上，这里是springboot_dlx_queue。
     * <p>
     * 当订单信息在ttl队列中存活时间超过60秒后，会被发送到springboot_dlx_queue队列上，
     * 这时@RabbitListener(queues = "springboot_dlx_queue")注解的listener方法就会被触发，自动取消订单。
     * <p>
     * 需要注意的是，ttl队列和DLX队列的绑定关系是在ttl队列的声明中设置的，即在ttlQueue()方法中设置：
     * <p>
     * 这里设置了ttl队列的死信交换机为DLX_EXCHANGE，死信路由键为"dlx.haha"，
     * 所以当订单信息在ttl队列中过期后，会被发送到DLX_EXCHANGE交换机并且路由键为"dlx.haha"的队列上，即springboot_dlx_queue队列。
     * <p>
     * 信息在ttl队列里面并不会被消费，只是等待超时后被自动转发到死信交换机，最终到达死信队列。
     * 这是因为ttl队列的消息会在一定时间后自动过期，但是如果没有设置死信交换机，那么过期的消息就会被直接丢弃，无法被消费。
     * 因此，通过设置死信交换机，可以将过期的消息转发到指定的死信队列中，以便后续进行处理。
     *
     * @return ttl队列
     */
    @Bean
    public Queue ttlQueue() {
        return QueueBuilder.durable(TTL_QUEUE)
                //该队列过期时间，单位毫秒
                .ttl(60000)
                //绑定死信交换机
                .deadLetterExchange(DLX_EXCHANGE)
                //设置死信交换机routingKey
                .deadLetterRoutingKey("dlx.haha")
                //设置队列最大消息数量为10
                .maxLength(100)
                .build();
    }

    /**
     * 声明死信交换机
     *
     * @return 死信交换机
     */
    @Bean
    public Exchange dlxExchange() {
        return ExchangeBuilder.topicExchange(DLX_EXCHANGE).build();
    }

    /**
     * 声明死信队列
     *
     * @return 死信队列
     */
    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable(DLX_QUEUE).build();
    }

    /**
     * 绑定ttl队列到ttl交换机
     *
     * @param queue    ttl队列
     * @param exchange ttl交换机
     */
    @Bean
    public Binding bindingTtl(@Qualifier("ttlQueue") Queue queue,
                              @Qualifier("ttlExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("ttl.#").noargs();
    }

    /**
     * 绑定死信队列到死信交换机
     *
     * @param queue    死信队列
     * @param exchange 死信交换机
     */
    @Bean
    public Binding bindingDlx(@Qualifier("dlxQueue") Queue queue,
                              @Qualifier("dlxExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("dlx.#").noargs();
    }


    @Bean
    public Queue refundQueue() {
        return new Queue(REFUND_QUEUE);
    }

    @Bean
    public Queue refundErrorQueue() {
        return new Queue(REFUND_ERROR_QUEUE);
    }


    /**
    * @Description： 以下两个Bean，创建Jackson2JsonMessageConverter对象，使得监听端可以接受任意类型的消息（主要是自定义的类型）
    * @Date: 2023/5/29 19:57
    * @Author: timeless
    */
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}