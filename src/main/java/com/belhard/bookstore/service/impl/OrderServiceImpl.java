package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.beans.Order;
import com.belhard.bookstore.dao.beans.OrderItem;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import com.belhard.bookstore.service.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderItemDao orderItemDao, UserService userService, BookService bookService) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public List<OrderDto> getAll() {
        return orderDao.getAllOrders().stream().map(this::orderToDto).toList();
    }

    @Override
    public OrderDto getById(Long id) {
        Order order = orderDao.getOrderById(id);
        return orderToDto(order);
    }

    private OrderDto orderToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalCost(order.getTotalCost());
        orderDto.setTimestamp(order.getTimestamp());
        orderDto.setStatusDto(OrderDto.StatusDto.valueOf(order.getStatus().toString()));
        UserDto userDto = userService.getById(order.getUserId());
        orderDto.setUserDto(userDto);
        List<OrderItem> items = orderItemDao.getOrderItemsByOrderId(order.getId());
        List<OrderItemDto> itemDtos = new ArrayList<>();
        for(OrderItem item : items) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setId(item.getId());
            orderItemDto.setQuantity(item.getQuantity());
            orderItemDto.setPrice(item.getPrice());
            BookDto bookDto = bookService.getById(item.getBookId());
            orderItemDto.setBookDto(bookDto);
            itemDtos.add(orderItemDto);
        }
        orderDto.setItems(itemDtos);
        return orderDto;
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
