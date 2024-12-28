package com.strr.rabbitmq.service;

import com.strr.api.constant.Constant;
import com.strr.api.model.Book;
import com.strr.rabbitmq.config.RabbitmqDirectConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import(RabbitmqDirectConfig.class)
@ConditionalOnProperty(name = "mode", havingValue = "direct")
public class RabbitmqConsumerDirectService {
    private final Logger logger = LoggerFactory.getLogger(RabbitmqConsumerDirectService.class);

    @RabbitListener(queues = Constant.RABBITMQ_QUEUE_DIRECT)
    public void listenDirect(Book book) {
        logger.info("Receive[direct]: Book[title={},author={}]", book.getTitle(), book.getAuthor());
    }
}
