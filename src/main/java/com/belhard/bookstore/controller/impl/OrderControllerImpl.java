package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.OrderController;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    @Override
    public String getAll(Model model) {
        List<OrderDto> orderDtos = orderService.getAll();
        model.addAttribute("orders", orderDtos);
        return "orders";
    }

    @GetMapping("/orders/user/{id}")
    @Override
    public String getAllByUserId(Model model, @PathVariable Long id) {
        List<OrderDto> orderDtosUser = orderService.getAll().stream().filter(o -> o.getId() == id).toList();
        model.addAttribute("orders", orderDtosUser);
        return "orders";
    }

    @GetMapping("/orders/{id}")
    @Override
    public String getById(Model model, @PathVariable Long id) {
        OrderDto orderDto = orderService.getById(id);
        model.addAttribute("order", orderDto);
        return "order";
    }

    @GetMapping("/orders/delete/{id}")
    @Override
    public String delete(Model model, @PathVariable Long id) {
        orderService.delete(id);
        model.addAttribute("id", id);
        return "orderdeleted";
    }

    @Override
    public String create(OrderDto orderDto, Model model) {
        return null;
    }

    @Override
    public String update(OrderDto orderDto, Model model) {
        return null;
    }
}
