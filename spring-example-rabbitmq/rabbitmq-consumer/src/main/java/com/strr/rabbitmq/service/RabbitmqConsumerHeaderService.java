package com.strr.rabbitmq.service;

import com.strr.api.constant.Constant;
import com.strr.rabbitmq.config.RabbitmqHeaderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import(RabbitmqHeaderConfig.class)
@ConditionalOnProperty(name = "mode", havingValue = "header")
public class RabbitmqConsumerHeaderService {
    private final Logger logger = LoggerFactory.getLogger(RabbitmqConsumerHeaderService.class);

    @RabbitListener(queues = Constant.RABBITMQ_QUEUE_HEADER_TITLE)
    public void listenHandlerTitle(byte[] msg) {
        logger.info("Receive[header-title]: {}", new String(msg));
    }

    @RabbitListener(queues = Constant.RABBITMQ_QUEUE_HEADER_AUTHOR)
    public void listenHandlerAuthor(byte[] msg) {
        logger.info("Receive[header-author]: {}", new String(msg));
    }
}
