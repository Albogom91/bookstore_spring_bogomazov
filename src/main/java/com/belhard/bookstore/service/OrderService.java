package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAll();

    OrderDto getById(Long id);

    OrderDto create(OrderDto orderDto);

    OrderDto update(OrderDto orderDto);

    void delete(Long id);
}
