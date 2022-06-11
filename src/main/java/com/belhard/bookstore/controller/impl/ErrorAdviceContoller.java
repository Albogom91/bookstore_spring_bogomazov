package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.exceptions.EntityAlreadyExistsException;
import com.belhard.bookstore.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorAdviceContoller {

    @ExceptionHandler
    public String handle(Model model, EntityNotFoundException e) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler
    public String handle(Model model, EntityAlreadyExistsException e) {
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler
    public String handle(Model model, NumberFormatException e) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("message", "Please, check your URL or provided arguments!");
        return "error";
    }

}
