package com.belhard.bookstore.controller.command.impl;

import com.belhard.bookstore.controller.Controller;
import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class UsersCommand implements Command {
    private static final UserService USER_SERVICE = Controller.getContext().getBean(UserServiceImpl.class);

    @Override
    public String execute(HttpServletRequest req) {
        List<UserDto> userDtos = USER_SERVICE.getAll();
        req.setAttribute("users", userDtos);
        return "jsp/users.jsp";
    }
}
