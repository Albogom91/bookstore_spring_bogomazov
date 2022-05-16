package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.Controller;
import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BookCommand implements Command {
    private static final BookService BOOK_SERVICE = Controller.getContext().getBean(BookServiceImpl.class);

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        BookDto bookDto = BOOK_SERVICE.getById(id);
        if (bookDto == null) {
            req.setAttribute("message", "There is no book with such id: " + id);
            return "jsp/error.jsp";
        }
        req.setAttribute("book", bookDto);
        return "jsp/book.jsp";

    }
}
