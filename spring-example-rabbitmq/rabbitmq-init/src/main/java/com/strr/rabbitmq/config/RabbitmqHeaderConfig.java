package com.strr.rabbitmq.config;

import com.strr.api.constant.Constant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 初始化消息队列
 */
public class RabbitmqHeaderConfig {
    @Bean
    protected HeadersExchange headersExchange() {
        return new HeadersExchange(Constant.RABBITMQ_EXCHANGE_HEADER, true, false);
    }

    @Bean
    protected Queue queueTitle() {
        return new Queue(Constant.RABBITMQ_QUEUE_HEADER_TITLE);
    }

    @Bean
    protected Queue queueAuthor() {
        return new Queue(Constant.RABBITMQ_QUEUE_HEADER_AUTHOR);
    }

    @Bean
    protected Binding bindingTitle() {
        return BindingBuilder.bind(queueTitle())
                .to(headersExchange())
                .where("title").exists();
    }

    @Bean
    protected Binding bindingAuthor() {
        Map<String, Object> map = new HashMap<>();
        map.put("author", "Craig Walls");
        return BindingBuilder.bind(queueAuthor())
                .to(headersExchange())
                .whereAny(map).match();
    }
}
