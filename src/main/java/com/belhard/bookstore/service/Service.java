package com.belhard.bookstore.service;

import java.util.List;

public interface Service<T, K> {

    List<T> getAll();

    T getById(K key);

    T create(T dto);

    T update(T dto);

    void delete(K key);
}
