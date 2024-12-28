package com.strr.kafka.config;

import com.strr.api.constant.Constant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic topic() {
        return new NewTopic(Constant.KAFKA_TOPIC, 1, (short) 1);
    }
}
