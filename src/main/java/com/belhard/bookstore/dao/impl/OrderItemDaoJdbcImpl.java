package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.beans.OrderItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemDaoJdbcImpl implements OrderItemDao {
    private static Logger logger = LogManager.getLogger(OrderItemDaoJdbcImpl.class);
    private static final String GET_ALL = "SELECT oi.id, oi.order_id, oi.book_id, oi.quantity, oi.price FROM orderitems oi;";
    private static final String GET_BY_ID = "SELECT oi.id, oi.order_id, oi.book_id, oi.quantity, oi.price FROM orderitems oi WHERE oi.id = ?";
    private static final String GET_BY_ORDER_ID = "SELECT oi.id, oi.order_id, oi.book_id, oi.quantity, oi.price FROM orderitems oi WHERE oi.order_id = ?";


    private final JdbcTemplate jdbcTemplate;
    private final OrderItemRowMapper orderItemRowMapper;

    @Autowired
    public OrderItemDaoJdbcImpl(JdbcTemplate jdbcTemplate, OrderItemRowMapper orderItemRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderItemRowMapper = orderItemRowMapper;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return jdbcTemplate.query(GET_ALL, orderItemRowMapper);
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, orderItemRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long id) {
        try {
            return jdbcTemplate.query(GET_BY_ORDER_ID, orderItemRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return null;
    }

    @Override
    public OrderItem updateOrder(OrderItem orderItem) {
        return null;
    }

    @Override
    public boolean deleteOrderItem(Long id) {
        return false;
    }
}
