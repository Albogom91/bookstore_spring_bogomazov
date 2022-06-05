package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.dto.BookDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface BookController extends Controller<String, Model, BookDto, Long> {

    String getCreate(Model model);

    String getAll(Model model, Map<String, String> map);

    String getUpdate(Model model, Long id);

    String countAll(Model model);

    String getByIsbn(Model model, String isbn);

    String getByAuthor(Model model, Map<String, String> map);

}
