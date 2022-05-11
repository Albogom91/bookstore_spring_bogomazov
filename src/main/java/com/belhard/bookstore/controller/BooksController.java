package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import com.belhard.bookstore.util.ControllerUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/books")
public class BooksController extends HttpServlet {
    private static final BookService BOOK_SERVICE = new BookServiceImpl();
    private static final String STYLE = "<style>" +
            "#t1 {" +
            "text-align: left;" +
            "border: 1px solid black" +
            "}" +
            "th, td {" +
            "border: 1px solid black;" +
            "padding: 10px" +
            "}" +
            "</style>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        PrintWriter out = resp.getWriter();
        List<BookDto> bookDtos = BOOK_SERVICE.getAll();
        out.write(STYLE + "<h1>Books</h1>");
        out.write("<table id=\"t1\" width=\"100%\">");
        out.write("<tr><th>ID</th><th>ISBN</th><th>TITLE</th><th>AUTHOR</th><th>PRICE</th><th>COVER</th></tr>");
        for (BookDto bookDto : bookDtos) {
            ControllerUtil.readBookDto(bookDto, out);
        }
        out.write("</table>");
    }

    private static void listFiles(String path, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
        File[] files = file.listFiles();
        for (File elem : files) {
            out.write((elem.getName()) + "\n");
            if (elem.isDirectory()) {
                listFiles(elem.getAbsolutePath(), resp);
            }
        }
    }
}
