package com.strr.rabbitmq.service;

import com.strr.api.constant.Constant;
import com.strr.api.model.Book;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class RabbitmqProducerService {
    private final RabbitTemplate rabbitTemplate;

    public RabbitmqProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendDirect(Book book) {
        rabbitTemplate.convertAndSend(Constant.RABBITMQ_QUEUE_DIRECT, book);
    }

    public void sendFanout(Book book) {
        rabbitTemplate.convertAndSend(Constant.RABBITMQ_EXCHANGE_FANOUT, null, book);
    }

    public void sendTopic(String topic, Book book) {
        rabbitTemplate.convertAndSend(Constant.RABBITMQ_EXCHANGE_TOPIC, topic, book);
    }

    public void sendHeader(Book book) {
        MessageBuilder builder = MessageBuilder.withBody(String.format("Book[title=%s]", book.getTitle()).getBytes(StandardCharsets.UTF_8));
        if (book.getTitle() != null) {
            builder.setHeader("title", book.getTitle());
        }
        if (book.getAuthor() != null) {
            builder.setHeader("author", book.getAuthor());
        }
        rabbitTemplate.convertAndSend(Constant.RABBITMQ_EXCHANGE_HEADER, null, builder.build());
    }
}
