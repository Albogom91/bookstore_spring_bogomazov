package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.OrderController;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;

    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @GetMapping
    public String getAll(Model model, @RequestParam Map<String, String> map) {
        Page<OrderDto> orders = orderService.getAll(PageUtil.getPageRequest(map));
        model.addAttribute("orders", orders.getContent());
        return "orders";
    }

    @GetMapping("/user")
    @Override
    public String getAllByUserId(Model model, @RequestParam Map<String, String> map) {
        Long id = Long.valueOf(map.get("id"));
        Page<OrderDto> orders = orderService.getAllByUserId(id, PageUtil.getPageRequest(map));
        model.addAttribute("orders", orders.getContent());
        return "orders";
    }

    @GetMapping("/{id}")
    @Override
    public String getById(Model model, @PathVariable Long id) {
        OrderDto orderDto = orderService.getById(id);
        model.addAttribute("order", orderDto);
        return "order";
    }

    @GetMapping("/delete/{id}")
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
