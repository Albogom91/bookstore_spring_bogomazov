package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.OrderDto;

import java.util.List;

public interface OrderService extends Service<OrderDto, Long> {

    List<OrderDto> getAllByUserId(Long id);

}
