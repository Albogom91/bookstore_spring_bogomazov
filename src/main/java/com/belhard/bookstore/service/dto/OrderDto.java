package com.belhard.bookstore.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderDto {
    private Long id;
    private UserDto userDto;
    private BigDecimal totalCost;
    private LocalDateTime timestamp;
    private StatusDto statusDto;
    private List<OrderItemDto> items;

    public OrderDto() {

    }

    public OrderDto(UserDto userDto, BigDecimal totalCost, LocalDateTime timestamp, StatusDto statusDto, List<OrderItemDto> items) {
        this.userDto = userDto;
        this.totalCost = totalCost;
        this.timestamp = timestamp;
        this.statusDto = statusDto;
        this.items = items;
    }

    public OrderDto(UserDto userDto, LocalDateTime timestamp, StatusDto statusDto, List<OrderItemDto> items) {
        this.userDto = userDto;
        this.timestamp = timestamp;
        this.statusDto = statusDto;
        this.items = items;
    }

    public enum StatusDto {
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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
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

    public StatusDto getStatusDto() {
        return statusDto;
    }

    public void setStatusDto(StatusDto statusDto) {
        this.statusDto = statusDto;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", userDto=" + userDto +
                ", totalCost=" + totalCost +
                ", timestamp=" + timestamp +
                ", statusDto=" + statusDto +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return id.equals(orderDto.id)
                && userDto.equals(orderDto.userDto)
                && totalCost.equals(orderDto.totalCost)
                && timestamp.equals(orderDto.timestamp)
                && statusDto == orderDto.statusDto
                && items.equals(orderDto.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userDto, totalCost, timestamp, statusDto, items);
    }
}
