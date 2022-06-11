package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService extends Service<OrderDto, Long> {

    Page<OrderDto> getAll(Pageable pageable);

    Page<OrderDto> getAllByUserId(Long id, Pageable pageable);

}
