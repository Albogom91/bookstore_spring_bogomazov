package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.beans.OrderItem;

import java.util.List;

public interface OrderItemDao {

    List<OrderItem> getAllOrderItems();

    OrderItem getOrderItemById(Long id);

    List<OrderItem> getOrderItemsByOrderId(Long id);

    OrderItem createOrderItem(OrderItem orderItem);

    OrderItem updateOrder(OrderItem orderItem);

    boolean deleteOrderItem(Long id);
}
