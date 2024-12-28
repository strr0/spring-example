package com.strr.mongodb.service;

import com.strr.api.IBookService;
import com.strr.api.model.Book;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongodbBookService implements IBookService {
    private final MongoTemplate mongoTemplate;

    public MongodbBookService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void save(Book book) {
        mongoTemplate.save(book);
    }

    @Override
    public Book get(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        List<Book> books = mongoTemplate.find(query, Book.class);
        return books.isEmpty() ? null : books.get(0);
    }

    @Override
    public void remove(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Book.class);
    }
}
