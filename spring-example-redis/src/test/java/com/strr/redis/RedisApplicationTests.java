package com.strr.redis;

import com.strr.api.model.Book;
import com.strr.redis.service.RedisBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisApplicationTests {
    @Autowired
    RedisBookService redisBookService;

    @Test
    void test() {
        // 保存
        Book book = new Book();
        book.setId(1001L);
        book.setTitle("Spring In Action");
        book.setAuthor("Craig Walls");
        redisBookService.save(book);

        // 获取
        Book newBook = redisBookService.get(1001L);
        System.out.printf("Book[title=%s,author=%s]\n", newBook.getTitle(), newBook.getAuthor());

        // 删除
        redisBookService.remove(1001L);
        Book empty = redisBookService.get(1001L);
        System.out.printf("Book is empty? %s\n", empty == null);
    }
}
