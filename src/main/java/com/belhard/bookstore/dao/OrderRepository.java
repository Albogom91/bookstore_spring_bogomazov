package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.beans.Order;
import com.belhard.bookstore.dao.beans.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByUser(User user, Pageable pageable);

    @Modifying
    @Query("update Order set status_id = 2 where id = ?1 and status_id != 2")
    void delete(Long id);

}
