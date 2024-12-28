package com.strr.rabbitmq.service;

import com.strr.api.constant.Constant;
import com.strr.api.model.Book;
import com.strr.rabbitmq.config.RabbitmqFanoutConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import(RabbitmqFanoutConfig.class)
@ConditionalOnProperty(name = "mode", havingValue = "fanout")
public class RabbitmqConsumerFanoutService {
    private final Logger logger = LoggerFactory.getLogger(RabbitmqConsumerFanoutService.class);

    @RabbitListener(queues = Constant.RABBITMQ_QUEUE_FANOUT_ONE)
    public void listenFanoutOne(Book book) {
        logger.info("Receive[fanout-one]: Book[title={},author={}]", book.getTitle(), book.getAuthor());
    }

    @RabbitListener(queues = Constant.RABBITMQ_QUEUE_FANOUT_TWO)
    public void listenFanoutTwo(Book book) {
        logger.info("Receive[fanout-two]: Book[title={},author={}]", book.getTitle(), book.getAuthor());
    }
}
