package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.dto.BookDto;
import org.springframework.ui.Model;

public interface BookController {

    String getById(Model model, Long id);

    String getAll(Model model);

    String getCreate(Model model);

    String create(BookDto bookDto, Model model);

    String getUpdate(Model model, Long id);

    String update(BookDto bookDto, Model model);

    String delete(Model model, Long id);

    String countAll(Model model);

    String getByIsbn(Model model, String isbn);

    String getByAuthor(Model model, String author);

}
