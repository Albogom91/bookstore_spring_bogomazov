package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.beans.OrderItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import java.util.List;

@Repository("orderItemDao")
public class OrderItemDaoJdbcImpl implements OrderItemDao {
    private static Logger logger = LogManager.getLogger(OrderItemDaoJdbcImpl.class);

    private static final String GET_ALL_ORDERITEMS = "from OrderItem";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderItem> getAll() {
        logger.debug("Database was accessed!");
        List<OrderItem> ois = entityManager.createQuery(GET_ALL_ORDERITEMS, OrderItem.class).getResultList();
        return ois;
    }

    @Override
    public OrderItem getById(Long id) {
        try {
            logger.debug("Database was accessed!");
            OrderItem orderItem = entityManager.find(OrderItem.class, id);
            return orderItem;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public OrderItem create(OrderItem orderItem) {
        logger.debug("Database was accessed!");
        entityManager.persist(orderItem);
        return orderItem;
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        logger.debug("Database was accessed!");
        entityManager.merge(orderItem);
        return orderItem;
    }

    @Override
    public boolean delete(Long id) {
        logger.debug("Database was accessed!");
        try {
            entityManager.remove(entityManager.find(OrderItem.class, id));
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            return false;
        }
    }
}
