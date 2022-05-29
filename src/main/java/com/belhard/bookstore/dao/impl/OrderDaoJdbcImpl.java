package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.beans.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("orderDao")
public class OrderDaoJdbcImpl implements OrderDao {
    private static Logger logger = LogManager.getLogger(OrderDaoJdbcImpl.class);
    private static final String GET_ALL_ORDERS = "from Order";
    private static final String DELETE = "update Order set status_id = 3 where id = ?1 and status_id != 3";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public OrderDaoJdbcImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = entityManager.createQuery(GET_ALL_ORDERS, Order.class).getResultList();
        return orders;
    }

    @Override
    public Order getOrderById(Long id) {
        try {
            Order order = entityManager.find(Order.class, id);
            return order;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    @Transactional
    public Order updateOrder(Order order) {
        entityManager.merge(order);
        return order;
    }

    @Override
    @Transactional
    public boolean deleteOrder(Long id) {
        int row = entityManager.createQuery(DELETE)
                .setParameter(1, id)
                .executeUpdate();
        return row == 1;
    }
}
