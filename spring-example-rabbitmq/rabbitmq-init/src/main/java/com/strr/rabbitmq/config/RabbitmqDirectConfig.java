package com.strr.rabbitmq.config;

import com.strr.api.constant.Constant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * 初始化消息队列
 */
public class RabbitmqDirectConfig {
    @Bean
    protected Queue queue() {
        return new Queue(Constant.RABBITMQ_QUEUE_DIRECT);
    }

    @Bean
    protected DirectExchange directExchange() {
        return new DirectExchange(Constant.RABBITMQ_EXCHANGE_DIRECT, true, false);
    }

    @Bean
    protected Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(directExchange())
                .with("routing-key-direct");
    }
}
