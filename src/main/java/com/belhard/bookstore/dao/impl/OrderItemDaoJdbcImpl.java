package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.beans.OrderItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderItemDaoJdbcImpl implements OrderItemDao {
    private static Logger logger = LogManager.getLogger(OrderItemDaoJdbcImpl.class);
    private static final String GET_ALL = "SELECT oi.id, oi.order_id, oi.book_id, oi.quantity, oi.price FROM orderitems oi WHERE deleted = false;";
    private static final String GET_BY_ID = "SELECT oi.id, oi.order_id, oi.book_id, oi.quantity, oi.price FROM orderitems oi WHERE oi.id = :id AND deleted = false";
    private static final String GET_BY_ORDER_ID = "SELECT oi.id, oi.order_id, oi.book_id, oi.quantity, oi.price FROM orderitems oi WHERE oi.order_id = :id AND deleted = false";
    private static final String DELETE = "UPDATE orderitems SET deleted = true WHERE id = :id AND deleted = false";
    private static final String CREATE = "INSERT INTO orderitems (order_id, book_id, quantity, price) " +
            "VALUES (:orderId, :bookId, :quantity, :price)";
    private static final String UPDATE = "UPDATE orderitems SET order_id = :orderId, book_id = :bookId, quantity = :quantity, " +
            "price = :price WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderItemRowMapper orderItemRowMapper;

    @Autowired
    public OrderItemDaoJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate, OrderItemRowMapper orderItemRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderItemRowMapper = orderItemRowMapper;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return jdbcTemplate.query(GET_ALL, orderItemRowMapper);
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, params, orderItemRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return jdbcTemplate.query(GET_BY_ORDER_ID, params, orderItemRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderItem.getOrderId());
        params.put("bookId", orderItem.getBookId());
        params.put("quantity", orderItem.getQuantity());
        params.put("price", orderItem.getPrice());
        SqlParameterSource source = new MapSqlParameterSource(params);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(CREATE, source, keyHolder, new String[]{"id"});
        if (result == 0) {
            throw new RuntimeException("Order item was not created: " + orderItem);
        }
        Number number = keyHolder.getKey();
        Long id = number.longValue();
        return getOrderItemById(id);
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderItem.getOrderId());
        params.put("bookId", orderItem.getBookId());
        params.put("quantity", orderItem.getQuantity());
        params.put("price", orderItem.getPrice());
        params.put("id", orderItem.getId());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int result = jdbcTemplate.update(UPDATE, source);
        if (result == 0) {
            throw new RuntimeException("Order item was not updated: " + orderItem);
        }
        return getOrderItemById(orderItem.getId());
    }

    @Override
    public boolean deleteOrderItem(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        SqlParameterSource source = new MapSqlParameterSource(params);
        int result = jdbcTemplate.update(DELETE, source);
        return result == 1;
    }
}
