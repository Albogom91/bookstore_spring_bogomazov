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

import java.math.BigDecimal;
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
        List<OrderItemDto> itemDtos = items.stream().map(oi -> orderItemToDto(oi)).toList();
        orderDto.setItems(itemDtos);
        return orderDto;
    }

    private OrderItemDto orderItemToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        BookDto bookDto = bookService.getById(orderItem.getBookId());
        orderItemDto.setBookDto(bookDto);
        return orderItemDto;
    }

    @Override
    public List<OrderDto> getAllByUserId(Long id) {
        List<OrderDto> ods = getAll();
        return ods.stream().filter(od -> od.getUserDto().getId() == id).toList();
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        orderDto.setTotalCost(calculateTotalCost(orderDto));
        Order order = dtoToOrder(orderDto);
        List<OrderItemDto> oids = orderDto.getItems();
        orderDto = orderToDto(orderDao.createOrder(order));
        Long id = orderDto.getId();
        List<OrderItem> ois = oids.stream().map(oid -> dtoToOrderItem(oid, id)).toList();
        ois = ois.stream().map(oi -> orderItemDao.createOrderItem(oi)).toList();
        oids = ois.stream().map(oi -> orderItemToDto(oi)).toList();
        orderDto.setItems(oids);
        return orderDto;
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        orderDto.setTotalCost(calculateTotalCost(orderDto));
        Order order = dtoToOrder(orderDto);
        List<OrderItem> oisDeleted = orderItemDao.getOrderItemsByOrderId(orderDto.getId());
        oisDeleted.stream().forEach(oi -> orderItemDao.deleteOrderItem(oi.getId()));
        Long id = orderDto.getId();
        List<OrderItem> ois = orderDto.getItems().stream().map(oid -> dtoToOrderItem(oid, id)).toList();
        ois.stream().forEach(oi -> orderItemDao.createOrderItem(oi));
        orderDto = orderToDto(orderDao.updateOrder(order));
        return getById(orderDto.getId());
    }

    private Order dtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setUserId(orderDto.getUserDto().getId());
        order.setTotalCost(orderDto.getTotalCost());
        order.setTimestamp(orderDto.getTimestamp());
        order.setStatus(Order.Status.valueOf(orderDto.getStatusDto().toString()));
        return order;
    }

    private OrderItem dtoToOrderItem(OrderItemDto orderItemDto, Long id) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDto.getId());
        orderItem.setOrderId(id);
        orderItem.setBookId(orderItemDto.getBookDto().getId());
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(orderItemDto.getPrice());
        return orderItem;
    }

    @Override
    public void delete(Long id) {
        logger.debug("Service method \"delete\" was called.");
        if (!orderDao.deleteOrder(id)) {
            logger.error("There is no order to delete with such id: " + id);
            throw new RuntimeException("There is no order to delete with such id: " + id);
        }
    }

    private BigDecimal calculateTotalCost(OrderDto orderDto) {
        BigDecimal totalCost = BigDecimal.ZERO;
        List<OrderItemDto> oids = orderDto.getItems();
        for (OrderItemDto oid : oids) {
            totalCost = totalCost.add(oid.getPrice().multiply(BigDecimal.valueOf(oid.getQuantity())));
        }
        return totalCost;
    }
}
