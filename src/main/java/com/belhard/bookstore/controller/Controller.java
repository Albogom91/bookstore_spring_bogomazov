package com.belhard.bookstore.controller;

import com.belhard.bookstore.ContextConfiguration;
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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static AnnotationConfigApplicationContext context;

    @Override
    public void init() throws ServletException {
        context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
    }

    public static AnnotationConfigApplicationContext getContext() {
        return context;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processReq(req, resp);

    }

    private void processReq (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("command");
        Command command = CommandFactory.getInstance().getCommand(action);
        String page = command.execute(req);
        req.getRequestDispatcher(page).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookCreateCommand.create(req, resp);

    }
}
