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

import java.util.List;

@Controller
public class UserControllerImpl implements UserController {
    private static Long updatedUserId = -1L;

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @Override
    public String getAll(Model model) {
        List<UserDto> userDtos = userService.getAll();
        model.addAttribute("users", userDtos);
        return "users";
    }

    @GetMapping("/users/{id}")
    @Override
    public String getById(Model model, @PathVariable Long id) {
        UserDto userDto = userService.getById(id);
        model.addAttribute("user", userDto);
        return "user";
    }

    @GetMapping(value = "/users/create")
    @Override
    public String getCreate(Model model) {
        model.addAttribute("user", new UserDto());
        return "userform";
    }

    @PostMapping("/users/create")
    @Override
    public String create(@ModelAttribute UserDto userDto, Model model) {
        userDto = userService.create(userDto);
        model.addAttribute("user", userDto);
        return "user";
    }

    @GetMapping(value = "/users/update/{id}")
    @Override
    public String getUpdate(Model model, @PathVariable Long id) {
        UserDto userDto = userService.getById(id);
        setUpdatedUserId(userDto.getId());
        model.addAttribute("user", userDto);
        return "userupdateform";
    }

    @PostMapping("/users/update/update")
    @Override
    public String update(@ModelAttribute UserDto userDto, Model model) {
        userDto.setId(getUpdatedUserId());
        userDto = userService.update(userDto);
        model.addAttribute("user", userDto);
        setUpdatedUserId(-1L);
        return "user";
    }

    private void setUpdatedUserId(Long id) {
        updatedUserId = id;
    }

    private Long getUpdatedUserId() {
        return updatedUserId;
    }

    @GetMapping("/users/delete/{id}")
    @Override
    public String delete(Model model, @PathVariable Long id) {
        userService.delete(id);
        model.addAttribute("id", id);
        return "userdeleted";
    }

    @GetMapping("/users/total")
    @Override
    public String countAll(Model model) {
        int result = userService.countAll();
        model.addAttribute("number", result);
        return "totalusersnumber";

    }

    @GetMapping("/users/email/{email}")
    @Override
    public String getByEmail(Model model, @PathVariable String email) {
        email = email.toLowerCase();
        UserDto userDto = userService.getByEmail(email);
        model.addAttribute("user", userDto);
        return "user";
    }

    @GetMapping("/users/lastname/{lastName}")
    @Override
    public String getByLastName(Model model, @PathVariable String lastName) {
        lastName = lastName.toUpperCase();
        List<UserDto> userDtos = userService.getByLastName(lastName);
        model.addAttribute("users", userDtos);
        return "users";
    }
}
