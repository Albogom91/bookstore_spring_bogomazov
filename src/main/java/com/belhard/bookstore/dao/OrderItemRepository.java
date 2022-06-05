package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.beans.Order;
import com.belhard.bookstore.dao.beans.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);
}
