package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.OrderItemRepository;
import com.belhard.bookstore.dao.OrderRepository;
import com.belhard.bookstore.dao.beans.Order;
import com.belhard.bookstore.dao.beans.OrderItem;
import com.belhard.bookstore.dao.beans.User;
import com.belhard.bookstore.exceptions.EntityNotFoundException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final UserService userService;
    private final BookService bookService;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderItemDao orderItemDao, UserService userService, BookService bookService) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public List<OrderDto> getAll() {
        logger.debug("Service method \"getAll\" was called.");
        return orderRepository.findAll().stream().map(this::orderToDto).toList();
    }

    @Override
    public Page<OrderDto> getAll(Pageable pageable) {

        return orderRepository.findAll(pageable).map(this::orderToDto);

    }

    @Override
    public OrderDto getById(Long id) {
        logger.debug("Service method \"getById\" was called.");
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is no order with such id: " + id);
                    return new EntityNotFoundException("There is no order with such id: " + id);
                });
        return orderToDto(order);
    }

    private OrderDto orderToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalCost(order.getTotalCost());
        orderDto.setTimestamp(order.getTimestamp());
        orderDto.setStatusDto(OrderDto.StatusDto.valueOf(order.getStatus().toString()));
        UserDto userDto = userService.userToDto(order.getUser());
        orderDto.setUserDto(userDto);
        orderDto.setItems(order.getOrderItems().stream().map(this::orderItemToDto).toList());
        return orderDto;
    }

    private OrderItemDto orderItemToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        BookDto bookDto = bookService.bookToDto(orderItem.getBook());
        orderItemDto.setBookDto(bookDto);
        return orderItemDto;
    }

    @Override
    public Page<OrderDto> getAllByUserId(Long id, Pageable pageable) {
        logger.debug("Service method \"getAllByUserId\" was called.");
        UserDto userDto = userService.getById(id);//To check if user exists
        Page<Order> orders = orderRepository.findByUser(userService.dtoToUser(userDto), pageable);
        return orders.map(this::orderToDto);
    }

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {
        logger.debug("Service method \"create\" was called.");
        orderDto.setTotalCost(calculateTotalCost(orderDto));
        Order order = dtoToOrder(orderDto);
        orderDto = orderToDto(orderRepository.save(order));
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto update(OrderDto orderDto) {
        logger.debug("Service method \"update\" was called.");
        orderDto.setTotalCost(calculateTotalCost(orderDto));
        Order order = dtoToOrder(orderDto);
        List<OrderItem> oisDeleted = orderItemRepository.findByOrder(orderRepository.getById(orderDto.getId()));
        oisDeleted.forEach(oi -> orderItemRepository.delete(oi));
        orderDto = orderToDto(orderRepository.save(order));
        return getById(orderDto.getId());
    }

    private Order dtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        User user = userService.dtoToUser(orderDto.getUserDto());
        order.setUser(user);
        order.setTotalCost(orderDto.getTotalCost());
        order.setTimestamp(orderDto.getTimestamp());
        order.setStatus(Order.Status.valueOf(orderDto.getStatusDto().toString()));
        List<OrderItem> orderItems = orderDto.getItems().stream().map(this::dtoToOrderItem).toList();
        orderItems.forEach(oi -> oi.setOrder(order));
        order.setOrderItems(orderItems);
        return order;
    }

    private OrderItem dtoToOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDto.getId());
        orderItem.setBook(bookService.dtoToBook(orderItemDto.getBookDto()));
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(orderItemDto.getPrice());
        return orderItem;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logger.debug("Service method \"delete\" was called.");
        getById(id);
        orderRepository.delete(id);
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
