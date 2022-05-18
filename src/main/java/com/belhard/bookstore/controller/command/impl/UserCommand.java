package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.Controller;
import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class UserCommand implements Command {
    private static final UserService USER_SERVICE = Controller.getContext().getBean("userService", UserServiceImpl.class);

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        UserDto userDto = USER_SERVICE.getById(id);
        if (userDto == null) {
            req.setAttribute("message", "There is no user with such id: " + id);
            return "jsp/error.jsp";
        }
        req.setAttribute("user", userDto);
        return "jsp/user.jsp";

    }
}
