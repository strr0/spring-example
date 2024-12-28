package com.strr.rabbitmq;

import com.strr.api.model.Book;
import com.strr.rabbitmq.service.RabbitmqProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqProducerApplicationTests {
    @Autowired
    RabbitmqProducerService rabbitmqProducerService;

    @Test
    void testDirect() {
        Book book = new Book();
        book.setId(1001L);
        book.setTitle("Spring In Action");
        book.setAuthor("Craig Walls");
        rabbitmqProducerService.sendDirect(book);
    }

    @Test
    void testFanout() {
        Book book = new Book();
        book.setId(1001L);
        book.setTitle("Spring In Action");
        book.setAuthor("Craig Walls");
        rabbitmqProducerService.sendFanout(book);
    }

    @Test
    void testTopic() {
        Book book1 = new Book();
        book1.setId(1001L);
        book1.setTitle("Spring In Action");
        book1.setAuthor("Craig Walls");
        rabbitmqProducerService.sendTopic("routing-key-topic-one.test", book1);

        Book book2 = new Book();
        book2.setId(1002L);
        book2.setTitle("Spring Boot In Action");
        book2.setAuthor("Craig Walls");
        rabbitmqProducerService.sendTopic("routing-key-topic-two.test", book2);

        Book book3 = new Book();
        book3.setId(1003L);
        book3.setTitle("Spring Microservices In Action");
        book3.setAuthor("John Carnell");
        rabbitmqProducerService.sendTopic("routing-key-topic-three.test", book3);
    }

    @Test
    void testHeader() {
        Book book1 = new Book();
        book1.setId(1001L);
        book1.setTitle("Spring In Action");
        book1.setAuthor("Craig Walls");
        rabbitmqProducerService.sendHeader(book1);

        Book book2 = new Book();
        book2.setId(1002L);
        book2.setTitle("Spring Boot In Action");
        rabbitmqProducerService.sendHeader(book2);
    }
}
