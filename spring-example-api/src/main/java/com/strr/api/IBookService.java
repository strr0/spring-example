package com.strr.api;

import com.strr.api.model.Book;

public interface IBookService {
    /**
     * 保存书本
     */
    void save(Book book);

    /**
     * 获取书本
     */
    Book get(Long id);

    /**
     * 删除书本
     */
    void remove(Long id);
}
