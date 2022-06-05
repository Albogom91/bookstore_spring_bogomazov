package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.BookController;
import com.belhard.bookstore.dao.BookRepository;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookControllerImpl implements BookController {
    private final BookService bookService;

    @Autowired
    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @GetMapping
    public String getAll(Model model, @RequestParam Map<String, String> map) {

        Page<BookDto> books = bookService.getAll(PageUtil.getPageRequest(map));
        model.addAttribute("books", books.getContent());

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
        bookDto = bookService.save(bookDto);
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
        bookDto = bookService.save(bookDto);
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

    @GetMapping("/author")
    @Override
    public String getByAuthor(Model model, @RequestParam Map<String, String> map) {

        String author = map.get("author");

        Page<BookDto> books = bookService.getByAuthor(author, PageUtil.getPageRequest(map));
        System.out.println(books);
        model.addAttribute("books", books.getContent());

        return "books";
    }
}
