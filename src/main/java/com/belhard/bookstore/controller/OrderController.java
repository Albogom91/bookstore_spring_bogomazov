package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.dto.OrderDto;
import org.springframework.ui.Model;

public interface OrderController {

    String getAll(Model model);

    String getAllByUserId(Model model, Long id);

    String getById(Model model, Long id);

    String create(OrderDto orderDto, Model model);

    String update(OrderDto orderDto, Model model);

    String delete(Model model, Long id);
}
