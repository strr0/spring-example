package com.strr.rabbitmq.config;

import com.strr.api.constant.Constant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * 初始化消息队列
 */
public class RabbitmqFanoutConfig {
    @Bean
    protected FanoutExchange fanoutExchange() {
        return new FanoutExchange(Constant.RABBITMQ_EXCHANGE_FANOUT, true, false);
    }

    @Bean
    protected Queue queueOne() {
        return new Queue(Constant.RABBITMQ_QUEUE_FANOUT_ONE);
    }

    @Bean
    protected Queue queueTwo() {
        return new Queue(Constant.RABBITMQ_QUEUE_FANOUT_TWO);
    }

    @Bean
    protected Binding bindingOne() {
        return BindingBuilder
                .bind(queueOne())
                .to(fanoutExchange());
    }

    @Bean
    protected Binding bindingTwo() {
        return BindingBuilder
                .bind(queueTwo())
                .to(fanoutExchange());
    }
}
