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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("orderItemDao")
public class OrderItemDaoJdbcImpl implements OrderItemDao {
    private static Logger logger = LogManager.getLogger(OrderItemDaoJdbcImpl.class);

    private static final String GET_ALL_ORDERITEMS = "from OrderItem where deleted = false";
    private static final String GET_BY_ORDER_ID = "select oi from OrderItem oi where oi.orderId = ?1 AND deleted = false";
    private static final String DELETE = "update OrderItem set deleted = true where id = ?1 and deleted = false";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public OrderItemDaoJdbcImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        List<OrderItem> ois = entityManager.createQuery(GET_ALL_ORDERITEMS, OrderItem.class).getResultList();
        return ois;
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        try {
            OrderItem orderItem = entityManager.find(OrderItem.class, id);
            return orderItem;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long id) {
        try {
            List<OrderItem> ois = entityManager.createQuery(GET_BY_ORDER_ID)
                    .setParameter(1, id)
                    .getResultList();
            return ois;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public OrderItem createOrderItem(OrderItem orderItem) {
        entityManager.persist(orderItem);
        return orderItem;
    }

    @Override
    @Transactional
    public OrderItem updateOrderItem(OrderItem orderItem) {
        entityManager.merge(orderItem);
        return orderItem;
    }

    @Override
    @Transactional
    public boolean deleteOrderItem(Long id) {
        int row = entityManager.createQuery(DELETE)
                .setParameter(1, id)
                .executeUpdate();
        return row == 1;
    }
}
