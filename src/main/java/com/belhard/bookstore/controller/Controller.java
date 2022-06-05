package com.belhard.bookstore.controller;

import org.springframework.ui.Model;

public interface Controller<S, M, T, K> {

    S getById(Model M, Long K);

    S create(T dto, Model M);

    S update(T dto, Model M);

    S delete(Model M, Long K);

}
