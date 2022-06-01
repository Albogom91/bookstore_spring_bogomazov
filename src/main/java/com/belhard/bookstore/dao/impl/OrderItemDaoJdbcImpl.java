package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.beans.OrderItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository("orderItemDao")
@Transactional
public class OrderItemDaoJdbcImpl implements OrderItemDao {
    private static Logger logger = LogManager.getLogger(OrderItemDaoJdbcImpl.class);

    private static final String GET_ALL_ORDERITEMS = "from OrderItem";
    private static final String GET_BY_ORDER_ID = "select oi from OrderItem oi where oi.order.id = ?1";
    private static final String DELETE = "update OrderItem set deleted = true where id = ?1 and deleted = false";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public OrderItemDaoJdbcImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        logger.debug("Database was accessed!");
        List<OrderItem> ois = entityManager.createQuery(GET_ALL_ORDERITEMS, OrderItem.class).getResultList();
        return ois;
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        try {
            logger.debug("Database was accessed!");
            OrderItem orderItem = entityManager.find(OrderItem.class, id);
            return orderItem;
        } catch (NoResultException e) {
            return null;
        }
    }

    /*@Override
    public List<OrderItem> getOrderItemsByOrderId(Long id) {
        try {
            logger.debug("Database was accessed!");
            List<OrderItem> ois = entityManager.createQuery(GET_BY_ORDER_ID)
                    .setParameter(1, id)
                    .getResultList();
            return ois;
        } catch (NoResultException e) {
            return null;
        }
    }*/

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        logger.debug("Database was accessed!");
        entityManager.persist(orderItem);
        return orderItem;
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        logger.debug("Database was accessed!");
        entityManager.merge(orderItem);
        return orderItem;
    }

    @Override
    public boolean deleteOrderItem(Long id) {
        logger.debug("Database was accessed!");
        System.out.println("--------------------------------------" + id);
        try {
            entityManager.remove(entityManager.find(OrderItem.class, id));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
        /*int row = entityManager.createQuery(DELETE)
                .setParameter(1, id)
                .executeUpdate();
        return row == 1;*/
    }
}
