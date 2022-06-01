package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.ui.Model;

public interface UserController extends Controller<String, Model, UserDto, Long> {

    String getCreate(Model model);

    String getUpdate(Model model, Long id);

    String countAll(Model model);

    String getByEmail(Model model, String email);

    String getByLastName(Model model, String lastName);

}
