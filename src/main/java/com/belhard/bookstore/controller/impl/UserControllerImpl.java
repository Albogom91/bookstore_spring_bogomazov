package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.UserController;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserControllerImpl implements UserController {
    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Override
    public String getAll(Model model) {
        List<UserDto> userDtos = userService.getAll();
        model.addAttribute("users", userDtos);
        return "users";
    }

    @GetMapping("/{id}")
    @Override
    public String getById(Model model, @PathVariable Long id) {
        UserDto userDto = userService.getById(id);
        model.addAttribute("user", userDto);
        return "user";
    }

    @GetMapping("/create")
    @Override
    public String getCreate(Model model) {
        model.addAttribute("user", new UserDto());
        return "userform";
    }

    @PostMapping("/create")
    @Override
    public String create(@ModelAttribute UserDto userDto, Model model) {
        userDto = userService.create(userDto);
        model.addAttribute("user", userDto);
        return "user";
    }

    @GetMapping("/update/{id}")
    @Override
    public String getUpdate(Model model, @PathVariable Long id) {
        UserDto userDto = userService.getById(id);
        model.addAttribute("user", userDto);
        return "userupdateform";
    }

    @PostMapping("/update/{id}")
    @Override
    public String update(@ModelAttribute UserDto userDto, Model model) {
        userDto = userService.update(userDto);
        model.addAttribute("user", userDto);
        return "user";
    }

    @GetMapping("/delete/{id}")
    @Override
    public String delete(Model model, @PathVariable Long id) {
        userService.delete(id);
        model.addAttribute("id", id);
        return "userdeleted";
    }

    @GetMapping("/total")
    @Override
    public String countAll(Model model) {
        Long result = userService.countAll();
        model.addAttribute("number", result);
        return "totalusersnumber";

    }

    @GetMapping("/email/{email}")
    @Override
    public String getByEmail(Model model, @PathVariable String email) {
        email = email.toLowerCase();
        UserDto userDto = userService.getByEmail(email);
        model.addAttribute("user", userDto);
        return "user";
    }

    @GetMapping("/lastname/{lastName}")
    @Override
    public String getByLastName(Model model, @PathVariable String lastName) {
        lastName = lastName.toUpperCase();
        List<UserDto> userDtos = userService.getByLastName(lastName);
        model.addAttribute("users", userDtos);
        return "users";
    }
}
