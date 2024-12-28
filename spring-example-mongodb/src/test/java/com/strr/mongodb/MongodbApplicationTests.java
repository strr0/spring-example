package com.strr.mongodb;

import com.strr.api.model.Book;
import com.strr.mongodb.service.MongodbBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MongodbApplicationTests {
    @Autowired
    MongodbBookService mongodbBookService;

    @Test
    void test() {
        // 保存
        Book book = new Book();
        book.setId(1001L);
        book.setTitle("Spring In Action");
        book.setAuthor("Craig Walls");
        mongodbBookService.save(book);

        // 获取
        Book newBook = mongodbBookService.get(1001L);
        System.out.printf("Book[title=%s,author=%s]\n", newBook.getTitle(), newBook.getAuthor());

        // 删除
        mongodbBookService.remove(1001L);
        Book empty = mongodbBookService.get(1001L);
        System.out.printf("Book is empty? %s\n", empty == null);
    }
}
