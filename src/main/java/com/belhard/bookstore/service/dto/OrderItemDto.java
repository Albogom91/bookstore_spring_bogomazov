package com.belhard.bookstore.service.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItemDto {
    private Long id;
    private BookDto bookDto;
    private Integer quantity;
    private BigDecimal price;

    public OrderItemDto() {

    }

    public OrderItemDto(BookDto bookDto, Integer quantity, BigDecimal price) {
        this.bookDto = bookDto;
        this.quantity = quantity;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BookDto getBookDto() {
        return bookDto;
    }

    public void setBookDto(BookDto bookDto) {
        this.bookDto = bookDto;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "id=" + id +
                ", bookDto=" + bookDto +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDto that = (OrderItemDto) o;
        return id.equals(that.id)
                && bookDto.equals(that.bookDto)
                && quantity.equals(that.quantity)
                && price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookDto, quantity, price);
    }
}
