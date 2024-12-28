package com.strr.kafka;

import com.strr.api.model.Book;
import com.strr.kafka.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaProducerApplicationTests {
    @Autowired
    KafkaProducerService kafkaProducerService;

    @Test
    void test() {
        Book book = new Book();
        book.setId(1001L);
        book.setTitle("Spring In Action");
        book.setAuthor("Craig Walls");
        kafkaProducerService.send(book);
    }
}
