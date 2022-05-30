package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.beans.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository("orderDao")
@Transactional
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
        logger.debug("Database was accessed!");
        List<Order> orders = entityManager.createQuery(GET_ALL_ORDERS, Order.class).getResultList();
        return orders;
    }

    @Override
    public Order getOrderById(Long id) {
        try {
            logger.debug("Database was accessed!");
            Order order = entityManager.find(Order.class, id);
            return order;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Order createOrder(Order order) {
        logger.debug("Database was accessed!");
        entityManager.persist(order);
        return order;
    }

    @Override
    public Order updateOrder(Order order) {
        logger.debug("Database was accessed!");
        entityManager.merge(order);
        return order;
    }

    @Override
    public boolean deleteOrder(Long id) {
        logger.debug("Database was accessed!");
        int row = entityManager.createQuery(DELETE)
                .setParameter(1, id)
                .executeUpdate();
        return row == 1;
    }
}
