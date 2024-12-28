package com.strr.kafka.service;

import com.strr.api.constant.Constant;
import com.strr.api.model.Book;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, Book> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Book> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Book book) {
        this.kafkaTemplate.send(Constant.KAFKA_TOPIC, book);
    }
}
