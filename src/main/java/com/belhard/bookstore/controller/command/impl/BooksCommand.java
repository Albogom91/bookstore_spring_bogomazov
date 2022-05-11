package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class BooksCommand implements Command {
    private static final BookService BOOK_SERVICE = new BookServiceImpl();

    @Override
    public String execute(HttpServletRequest req) {
        List<BookDto> bookDtos = BOOK_SERVICE.getAll();
        req.setAttribute("books", bookDtos);
        return "jsp/books.jsp";
    }
}
