package com.belhard.bookstore.dao;

import java.util.List;

public interface EntityDao<T, K> {

    List<T> getAll();

    T getById(K key);

    T create(T entity);

    T update(T entity);

    boolean delete(K key);
}
