package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.impl.UserServiceImpl;
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

@WebServlet("/users")
public class UsersController extends HttpServlet {
    private static final UserService USER_SERVICE = new UserServiceImpl();
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
        List<UserDto> userDtos = USER_SERVICE.getAll();
        out.write(STYLE + "<h1>Users</h1>");
        out.write("<table id=\"t1\" width=\"100%\">");
        out.write("<tr><th>ID</th><th>First name</th><th>Last name</th><th>Email</th><th>Password</th><th>Role</th></tr>");
        for (UserDto userDto : userDtos) {
            ControllerUtil.readUserDto(userDto, out);
        }
        out.write("</table>");
    }
}
