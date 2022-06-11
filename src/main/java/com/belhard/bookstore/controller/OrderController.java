package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.dto.OrderDto;
import org.springframework.ui.Model;

import java.util.Map;

public interface OrderController extends Controller<String, Model, OrderDto, Long> {

    String getAll(Model model, Map<String, String> map);

    String getAllByUserId(Model model, Map<String, String> map);

}
