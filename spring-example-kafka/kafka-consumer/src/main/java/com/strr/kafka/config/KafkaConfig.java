package com.strr.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;

@Configuration
public class KafkaConfig {
    // 消息转换
    @Bean
    public RecordMessageConverter converter() {
        return new JsonMessageConverter();
    }
}
