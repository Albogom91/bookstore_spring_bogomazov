package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.BookController;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookControllerImpl implements BookController {
    private final BookService bookService;

    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
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
        return "book";
    }

    @GetMapping(value = "/create")
    @Override
    public String getCreate(Model model) {
        model.addAttribute("book", new BookDto());
        return "bookform";
    }

    @PostMapping("/create")
    @Override
    public String create(@ModelAttribute BookDto bookDto, Model model) {
        bookDto = bookService.create(bookDto);
        model.addAttribute("book", bookDto);
        return "book";
    }

    @GetMapping(value = "/update/{id}")
    @Override
    public String getUpdate(Model model, @PathVariable Long id) {
        BookDto bookDto = bookService.getById(id);
        model.addAttribute("book", bookDto);
        return "bookupdateform";
    }

    @PostMapping("/update/{id}")
    @Override
    public String update(@ModelAttribute BookDto bookDto, Model model) {
        bookDto = bookService.update(bookDto);
        model.addAttribute("book", bookDto);
        return "book";
    }

    @GetMapping("/delete/{id}")
    @Override
    public String delete(Model model, @PathVariable Long id) {
        bookService.delete(id);
        model.addAttribute("id", id);
        return "bookdeleted";
    }

    @GetMapping("/total")
    @Override
    public String countAll(Model model) {
        Long result = bookService.countAll();
        model.addAttribute("number", result);
        return "totalbooksnumber";

    }

    @GetMapping("/isbn/{isbn}")
    @Override
    public String getByIsbn(Model model, @PathVariable String isbn) {
        BookDto bookDto = bookService.getByIsbn(isbn);
        model.addAttribute("book", bookDto);
        return "book";
    }

    @GetMapping("/author/{author}")
    @Override
    public String getByAuthor(Model model, @PathVariable String author) {
        author = author.toUpperCase();
        List<BookDto> bookDtos = bookService.getByAuthor(author);
        model.addAttribute("books", bookDtos);
        return "books";
    }
}
