package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OrderControllerImpl {
    private final OrderService orderService;

    @Autowired
    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    //@Override
    public String getAll(Model model) {
        List<OrderDto> orderDtos = orderService.getAll();
        model.addAttribute("orders", orderDtos);
        return "orders";
    }

    @GetMapping("/orders/{id}")
    //@Override
    public String getById(Model model, @PathVariable Long id) {
        OrderDto orderDto = orderService.getById(id);
        model.addAttribute("order", orderDto);
        return "order";
    }
}
