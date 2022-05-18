package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.Controller;
import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.controller.command.CommandFactory;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class BookCreateCommand implements Command {
    private static final BookService BOOK_SERVICE = Controller.getContext().getBean("bookService", BookServiceImpl.class);

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/bookform.jsp";
    }

    public static void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        BookDto bookDto = new BookDto();
        bookDto.setIsbn(req.getParameter("isbn"));
        bookDto.setTitle(req.getParameter("title"));
        bookDto.setAuthor(req.getParameter("author"));
        bookDto.setCoverDto(BookDto.CoverDto.valueOf(req.getParameter("cover").toUpperCase()));
        bookDto.setPrice(BigDecimal.valueOf(Double.valueOf(req.getParameter("price"))));
        bookDto = BOOK_SERVICE.create(bookDto);
        req.setAttribute("book", bookDto);
        req.getRequestDispatcher("jsp/book.jsp").forward(req, resp);
    }
}
