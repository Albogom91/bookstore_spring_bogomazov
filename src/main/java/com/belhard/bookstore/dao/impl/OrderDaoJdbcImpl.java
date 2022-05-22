package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.beans.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoJdbcImpl implements OrderDao {
    private static Logger logger = LogManager.getLogger(OrderDaoJdbcImpl.class);
    private static final String GET_ALL = "SELECT o.id, o.user_id, o.totalcost, o.timestamp, s.name AS status " +
            "FROM orders o JOIN statuses s ON o.status_id = s.id";
    private static final String GET_BY_ID = "SELECT o.id, o.user_id, o.totalcost, o.timestamp, s.name AS status " +
            "FROM orders o JOIN statuses s ON o.status_id = s.id WHERE o.id = ?";

    /*private static final String CREATE = "INSERT INTO books (isbn, title, author, price, cover_id) " +
            "VALUES (?, ?, ?, ?, (SELECT id FROM covers WHERE name = ?))";
    private static final String UPDATE = "UPDATE books SET isbn = ?, title = ?, author = ?, price = ?, cover_id = (SELECT id FROM covers WHERE name = ?) " +
            "WHERE id = ? AND deleted = false";
    private static final String DELETE = "UPDATE books SET deleted = true WHERE id = ? AND deleted = false";
    private static final String COUNT_ALL_BOOKS = "SELECT COUNT(*) AS count FROM books WHERE deleted = false";
    private static final String GET_BY_ISBN = "SELECT b.id, b.isbn, b.title, b.author, b.price, c.name AS cover " +
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.isbn = ? AND deleted = false";
    private static final String GET_BY_AUTHOR = "SELECT b.id, b.isbn, b.title, b.author, b.price, c.name AS cover " +
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE UPPER(b.author) = ? AND deleted = false";*/

    private final JdbcTemplate jdbcTemplate;
    private final OrderRowMapper orderRowMapper;

    @Autowired
    public OrderDaoJdbcImpl(JdbcTemplate jdbcTemplate, OrderRowMapper orderRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRowMapper = orderRowMapper;
    }

    @Override
    public List<Order> getAllOrders() {
        return jdbcTemplate.query(GET_ALL, orderRowMapper);
    }

    @Override
    public Order getOrderById(Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, orderRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public Order updateOrder(Order order) {
        return null;
    }

    @Override
    public boolean deleteOrder(Long id) {
        return false;
    }
}
