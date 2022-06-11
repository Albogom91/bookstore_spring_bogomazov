package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.UserController;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserControllerImpl implements UserController {
    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping
    public String getAll(Model model, @RequestParam Map<String, String> map) {

        Page<UserDto> users = userService.getAll(PageUtil.getPageRequest(map));
        model.addAttribute("users", users.getContent());

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
        userDto = userService.save(userDto);
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
        userDto = userService.save(userDto);
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

    @GetMapping("/lastname")
    @Override
    public String getByLastName(Model model, @RequestParam Map<String, String> map) {

        String lastName = map.get("lastname");

        Page<UserDto> users = userService.getByLastName(lastName, PageUtil.getPageRequest(map));
        model.addAttribute("users", users.getContent());
        return "users";
    }
}
