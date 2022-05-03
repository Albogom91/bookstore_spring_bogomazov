package com.belhard.bookstore.dao.beans;

import java.math.BigDecimal;
import java.util.Objects;

public class Book {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private BigDecimal price;
    private Cover cover;

    public Book() {

    }

    public Book(String isbn, String title, String author, BigDecimal price, Cover cover) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.cover = cover;
    }

    public enum Cover {
        SOFT,
        HARD,
        SPECIAL
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", cover=" + cover +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id)
                && Objects.equals(isbn, book.isbn)
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && Objects.equals(price, book.price)
                && cover == book.cover;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title, author, price, cover);
    }
}

