package com.strr.kafka.service;

import com.strr.api.constant.Constant;
import com.strr.api.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(id = Constant.KAFKA_GROUP, topics = Constant.KAFKA_TOPIC)
    public void receive(Book book) {
        logger.info("Receive: Book[title={},author={}]", book.getTitle(), book.getAuthor());
    }
}
