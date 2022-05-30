package com.belhard.bookstore.dao.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    //targetEntity = com.belhard.bookstore.dao.beans.User.class
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "totalcost")
    private BigDecimal totalCost;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_id")
    private Status status;

    public Order() {

    }

    public Order(User user, BigDecimal totalCost, LocalDateTime timestamp, Status status) {
        this.user = user;
        this.totalCost = totalCost;
        this.timestamp = timestamp;
        this.status = status;
    }

    public enum Status {
        INVALID,
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id)
                && Objects.equals(user, order.user)
                && Objects.equals(totalCost, order.totalCost)
                && Objects.equals(timestamp, order.timestamp)
                && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, totalCost, timestamp, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", totalCost=" + totalCost +
                ", timestamp=" + timestamp +
                ", status=" + status +
                '}';
    }
}

