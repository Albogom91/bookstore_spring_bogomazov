package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.BookController;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookControllerImpl implements BookController {
    private BookService bookService;

    public BookControllerImpl() {

    }

    @Autowired
    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    public void setBookDao(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    @Override
    public String getAll(Model model) {
        List<BookDto> bookDtos = bookService.getAll();
        model.addAttribute("books", bookDtos);
        return "books";
    }

    @GetMapping("/{id}")
    @Override
    public String getById(Model model, @PathVariable Long id) {
        BookDto bookDto = bookService.getById(id);
        model.addAttribute("book", bookDto);
        System.out.println(bookDto);
        return "book";
    }
}
