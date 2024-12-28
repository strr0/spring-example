package com.strr.rabbitmq.service;

import com.strr.api.constant.Constant;
import com.strr.api.model.Book;
import com.strr.rabbitmq.config.RabbitmqTopicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import(RabbitmqTopicConfig.class)
@ConditionalOnProperty(name = "mode", havingValue = "topic")
public class RabbitmqConsumerTopicService {
    private final Logger logger = LoggerFactory.getLogger(RabbitmqConsumerTopicService.class);

    @RabbitListener(queues = Constant.RABBITMQ_QUEUE_TOPIC_ONE)
    public void listenTopicOne(Book book) {
        logger.info("Receive[topic-one]: Book[title={},author={}]", book.getTitle(), book.getAuthor());
    }

    @RabbitListener(queues = Constant.RABBITMQ_QUEUE_TOPIC_TWO)
    public void listenTopicTwo(Book book) {
        logger.info("Receive[topic-two]: Book[title={},author={}]", book.getTitle(), book.getAuthor());
    }

    @RabbitListener(queues = Constant.RABBITMQ_QUEUE_TOPIC_THREE)
    public void listenTopicThree(Book book) {
        logger.info("Receive[topic-three]: Book[title={},author={}]", book.getTitle(), book.getAuthor());
    }
}
