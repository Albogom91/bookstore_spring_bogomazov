package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user")
public class UserController extends HttpServlet {
    private static final UserService USER_SERVICE = new UserServiceImpl();
    private static final String STYLE = "<style>" +
            "* {" +
            "font-style: italic;" +
            "text-align: center" +
            "}" +
            "</style>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        Long id = Long.valueOf(req.getParameter("id"));
        UserDto userDto = USER_SERVICE.getById(id);
        PrintWriter out = resp.getWriter();
        out.write(STYLE + "<h1>User, id = " + userDto.getId() + " </h1>");
        out.write("<h2>" + userDto.getFirstName() + " " + userDto.getLastName() + ", " + userDto.getRoleDto().toString().toLowerCase() + "</h2>");
        out.write("<h3>Email = " + userDto.getEmail());
        out.write("<h3>Password = " + userDto.getPassword());
    }
}
