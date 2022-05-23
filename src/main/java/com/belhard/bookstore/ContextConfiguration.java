package com.belhard.bookstore;

import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ContextConfiguration {
    private static OrderService orderService;
    private static UserService userService;
    private static BookService bookService;

    @Autowired
    public ContextConfiguration(OrderService orderService, UserService userService, BookService bookService) {
        this.orderService = orderService;
        this.userService = userService;
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ContextConfiguration.class, args);

        System.out.println(orderService.getById(5L));
        UserDto user = userService.getById(3L);
        UserDto user1 = userService.getById(8L);
        BookDto book = bookService.getById(7L);
        BookDto book1 = bookService.getById(8L);
        BookDto book2 = bookService.getById(9L);

        OrderItemDto oi = new OrderItemDto(book, 2, BigDecimal.valueOf(19.25));
        OrderItemDto oi1 = new OrderItemDto(book1, 1, BigDecimal.valueOf(7.75));
        OrderItemDto oi2 = new OrderItemDto(book2, 1, BigDecimal.valueOf(8.75));
        List<OrderItemDto> ois = new ArrayList<>();
        ois.add(oi);
        ois.add(oi1);

        OrderDto order = new OrderDto(user, LocalDateTime.now(), OrderDto.StatusDto.PENDING, ois);
        System.out.println(order);
        order = orderService.create(order);
        System.out.println(order);

        order.setUserDto(user1);
        ois.remove(oi1);
        ois.add(oi2);

        order.setItems(ois);
        System.out.println(order);
        order = orderService.update(order);
        System.out.println(order);

    }

}
