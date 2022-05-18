package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.dto.BookDto;
import org.springframework.ui.Model;

import java.util.List;

public interface BookController {

    String getById(Model model, Long id);

    String getAll(Model model);
}
