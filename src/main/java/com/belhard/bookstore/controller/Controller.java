package com.belhard.bookstore.controller;

import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.controller.command.CommandFactory;
import com.belhard.bookstore.controller.command.impl.BookCreateCommand;
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
import java.math.BigDecimal;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final BookService BOOK_SERVICE = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processReq(req, resp);

    }

    private void processReq (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("command");
        System.out.println(action);
        Command command = CommandFactory.getInstance().getCommand(action);
        System.out.println(action);
        String page = command.execute(req);
        req.getRequestDispatcher(page).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookCreateCommand.create(req, resp);

    }
}
