package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.dto.OrderDto;
import org.springframework.ui.Model;

public interface OrderController extends Controller<String, Model, OrderDto, Long> {

    String getAllByUserId(Model model, Long id);

}
