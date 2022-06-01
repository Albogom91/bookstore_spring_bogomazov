package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.dto.BookDto;
import org.springframework.ui.Model;

public interface BookController extends Controller<String, Model, BookDto, Long> {

    String getCreate(Model model);

    String getUpdate(Model model, Long id);

    String countAll(Model model);

    String getByIsbn(Model model, String isbn);

    String getByAuthor(Model model, String author);

}
