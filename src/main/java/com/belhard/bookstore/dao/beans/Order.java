package com.belhard.bookstore.dao.beans;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private Long id;
    private Long userId;
    private BigDecimal totalCost;
    private LocalDateTime timestamp;
    private Status status;

    public Order() {

    }

    public Order(Long userId, BigDecimal totalCost, LocalDateTime timestamp, Status status) {
        this.userId = userId;
        this.totalCost = totalCost;
        this.timestamp = timestamp;
        this.status = status;
    }

    public enum Status {
        PENDING,
        COMPLETED,
        CANCELLED
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalCost=" + totalCost +
                ", timestamp=" + timestamp +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id)
                && userId.equals(order.userId)
                && totalCost.equals(order.totalCost)
                && timestamp.equals(order.timestamp)
                && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, totalCost, timestamp, status);
    }
}

