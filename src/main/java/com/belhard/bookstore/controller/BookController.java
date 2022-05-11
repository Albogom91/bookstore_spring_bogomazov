package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/book")
public class BookController extends HttpServlet {
    private static final BookService BOOK_SERVICE = new BookServiceImpl();
    private static final String STYLE = "<style>" +
            "* {" +
            "font-style: italic;" +
            "text-align: center" +
            "}" +
            "</style>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        BookDto bookDto = BOOK_SERVICE.getById(id);
        if (bookDto == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        resp.setStatus(200);
        PrintWriter out = resp.getWriter();
        out.write(STYLE + "<h1>Book, id = " + bookDto.getId() + " </h1>");
        out.write("<h2>" + bookDto.getTitle() + " by " + bookDto.getAuthor() + " in " + bookDto.getCoverDto().toString().toLowerCase() + " cover</h2>");
        out.write("<h3>ISBN = " + bookDto.getIsbn());
        out.write("<h4>Price = " + bookDto.getPrice() + "</h4>");
    }
}
