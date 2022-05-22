package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.ui.Model;

public interface UserController {

    String getById(Model model, Long id);

    String getAll(Model model);

    String getCreate(Model model);

    String create(UserDto userDto, Model model);

    String getUpdate(Model model, Long id);

    String update(UserDto userDto, Model model);

    String delete(Model model, Long id);

    String countAll(Model model);

    String getByEmail(Model model, String email);

    String getByLastName(Model model, String lastName);

}
