package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.beans.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoJdbcImpl implements OrderDao {
    private static Logger logger = LogManager.getLogger(OrderDaoJdbcImpl.class);
    private static final String GET_ALL = "SELECT o.id, o.user_id, o.totalcost, o.timestamp, s.name AS status " +
            "FROM orders o JOIN statuses s ON o.status_id = s.id";
    private static final String GET_BY_ID = "SELECT o.id, o.user_id, o.totalcost, o.timestamp, s.name AS status " +
            "FROM orders o JOIN statuses s ON o.status_id = s.id WHERE o.id = :id";
    private static final String DELETE = "UPDATE orders SET status_id = (SELECT id FROM statuses WHERE name = 'CANCELLED') WHERE id = :id " +
            "AND status_id != (SELECT id FROM statuses WHERE name = 'CANCELLED')";
    private static final String CREATE = "INSERT INTO orders (user_id, totalcost, timestamp, status_id) " +
            "VALUES (:userId, :totalCost, :timestamp, (SELECT id FROM statuses WHERE name = :status))";
    private static final String UPDATE = "UPDATE orders SET user_id = :userId, totalcost = :totalCost, timestamp = :timestamp, " +
            "status_id = (SELECT id FROM statuses WHERE name = :status) WHERE id = :id";

    /*private static final String CREATE = "INSERT INTO books (isbn, title, author, price, cover_id) " +
            "VALUES (?, ?, ?, ?, (SELECT id FROM covers WHERE name = ?))";
    private static final String UPDATE = "UPDATE books SET isbn = ?, title = ?, author = ?, price = ?, cover_id = (SELECT id FROM covers WHERE name = ?) " +
            "WHERE id = ? AND deleted = false";
    private static final String COUNT_ALL_BOOKS = "SELECT COUNT(*) AS count FROM books WHERE deleted = false";
    private static final String GET_BY_ISBN = "SELECT b.id, b.isbn, b.title, b.author, b.price, c.name AS cover " +
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.isbn = ? AND deleted = false";
    private static final String GET_BY_AUTHOR = "SELECT b.id, b.isbn, b.title, b.author, b.price, c.name AS cover " +
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE UPPER(b.author) = ? AND deleted = false";*/

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderRowMapper orderRowMapper;

    @Autowired
    public OrderDaoJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate, OrderRowMapper orderRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRowMapper = orderRowMapper;
    }

    @Override
    public List<Order> getAllOrders() {
        return jdbcTemplate.query(GET_ALL, orderRowMapper);
    }

    @Override
    public Order getOrderById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, params, orderRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Order createOrder(Order order) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", order.getUserId());
        params.put("totalCost", order.getTotalCost());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        params.put("timestamp", order.getTimestamp().format(formatter));
        params.put("status", order.getStatus().toString());
        SqlParameterSource source = new MapSqlParameterSource(params);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(CREATE, source, keyHolder, new String[]{"id"});
        if (result == 0) {
            throw new RuntimeException("Order was not created: " + order);
        }
        Number number = keyHolder.getKey();
        Long id = number.longValue();
        return getOrderById(id);
    }

    @Override
    public Order updateOrder(Order order) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", order.getUserId());
        params.put("totalCost", order.getTotalCost());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        params.put("timestamp", order.getTimestamp().format(formatter));
        params.put("status", order.getStatus().toString());
        params.put("id", order.getId());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int result = jdbcTemplate.update(UPDATE, source);
        if (result == 0) {
            throw new RuntimeException("Order was not updated: " + order);
        }
        return getOrderById(order.getId());
    }

    @Override
    public boolean deleteOrder(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        SqlParameterSource source = new MapSqlParameterSource(params);
        int result = jdbcTemplate.update(DELETE, source);
        return result == 1;
    }
}
