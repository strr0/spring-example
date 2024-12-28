package com.strr.rabbitmq.config;

import com.strr.api.constant.Constant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;

/**
 * 初始化消息队列
 */
public class RabbitmqTopicConfig {
    @Bean
    protected TopicExchange topicExchange() {
        return new TopicExchange(Constant.RABBITMQ_EXCHANGE_TOPIC, true, false);
    }

    @Bean
    protected Queue queueOne() {
        return new Queue(Constant.RABBITMQ_QUEUE_TOPIC_ONE);
    }

    @Bean
    protected Queue queueTwo() {
        return new Queue(Constant.RABBITMQ_QUEUE_TOPIC_TWO);
    }

    @Bean
    protected Queue queueThree() {
        return new Queue(Constant.RABBITMQ_QUEUE_TOPIC_THREE);
    }

    @Bean
    protected Binding bindingOne() {
        return BindingBuilder
                .bind(queueOne())
                .to(topicExchange())
                .with("routing-key-topic-one.#");
    }

    @Bean
    protected Binding bindingTwo() {
        return BindingBuilder
                .bind(queueTwo())
                .to(topicExchange())
                .with("routing-key-topic-two.#");
    }

    @Bean
    protected Binding bindingThree() {
        return BindingBuilder
                .bind(queueThree())
                .to(topicExchange())
                .with("routing-key-topic-three.#");
    }
}
