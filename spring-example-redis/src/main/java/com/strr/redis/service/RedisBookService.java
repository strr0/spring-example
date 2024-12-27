package com.strr.redis.service;

import com.strr.api.IBookService;
import com.strr.api.model.Book;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisBookService implements IBookService {
    @Resource
    private RedisTemplate<Long, Book> redisTemplate;

    @Override
    public void save(Book book) {
        Long id = book.getId();
        if (id != null) {
            redisTemplate.opsForValue().set(id, book);
        }
    }

    @Override
    public Book get(Long id) {
        return redisTemplate.opsForValue().get(id);
    }

    @Override
    public void remove(Long id) {
        redisTemplate.delete(id);
    }
}
