package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.BookController;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BookControllerImpl implements BookController {
    private static Long updatedBookId = -1L;

    private final BookService bookService;

    @Autowired
    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/books")
    @Override
    public String getAll(Model model) {
        List<BookDto> bookDtos = bookService.getAll();
        model.addAttribute("books", bookDtos);
        return "books";
    }

    @GetMapping("/books/{id}")
    @Override
    public String getById(Model model, @PathVariable Long id) {
        BookDto bookDto = bookService.getById(id);
        model.addAttribute("book", bookDto);
        return "book";
    }

    @GetMapping(value = "/books/create")
    @Override
    public String getCreate(Model model) {
        model.addAttribute("book", new BookDto());
        return "bookform";
    }

    @PostMapping("/books/create")
    @Override
    public String create(@ModelAttribute BookDto bookDto, Model model) {
        bookDto = bookService.create(bookDto);
        model.addAttribute("book", bookDto);
        return "book";
    }

    @GetMapping(value = "/books/update/{id}")
    @Override
    public String getUpdate(Model model, @PathVariable Long id) {
        BookDto bookDto = bookService.getById(id);
        setUpdatedBookId(bookDto.getId());
        model.addAttribute("book", bookDto);
        return "bookupdateform";
    }

    @PostMapping("/books/update/update")
    @Override
    public String update(@ModelAttribute BookDto bookDto, Model model) {
        bookDto.setId(getUpdatedBookId());
        bookDto = bookService.update(bookDto);
        model.addAttribute("book", bookDto);
        setUpdatedBookId(-1L);
        return "book";
    }

    private void setUpdatedBookId(Long id) {
        updatedBookId = id;
    }

    private Long getUpdatedBookId() {
        return updatedBookId;
    }

    @GetMapping("/books/delete/{id}")
    @Override
    public String delete(Model model, @PathVariable Long id) {
        bookService.delete(id);
        model.addAttribute("id", id);
        return "bookdeleted";
    }

    @GetMapping("/books/total")
    @Override
    public String countAll(Model model) {
        int result = bookService.countAll();
        model.addAttribute("number", result);
        return "totalbooksnumber";

    }

    @GetMapping("/books/isbn/{isbn}")
    @Override
    public String getByIsbn(Model model, @PathVariable String isbn) {
        BookDto bookDto = bookService.getByIsbn(isbn);
        model.addAttribute("book", bookDto);
        return "book";
    }

    @GetMapping("/books/author/{author}")
    @Override
    public String getByAuthor(Model model, @PathVariable String author) {
        author = author.toUpperCase();
        List<BookDto> bookDtos = bookService.getByAuthor(author);
        model.addAttribute("books", bookDtos);
        return "books";
    }
}
