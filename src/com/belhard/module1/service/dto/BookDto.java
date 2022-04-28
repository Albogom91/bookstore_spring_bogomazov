package com.belhard.module1.service.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class BookDto {

        private Long id;
        private String isbn;
        private String title;
        private String author;
        private BigDecimal price;
        private CoverDto coverDto;

        public BookDto() {

        }

        public BookDto(String isbn, String title, String author, BigDecimal price, CoverDto coverDto) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.price = price;
            this.coverDto = coverDto;
        }

        public enum CoverDto {
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

        public CoverDto getCover() {
            return coverDto;
        }

        public void setCover(CoverDto coverDto) {
            this.coverDto = coverDto;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "id=" + id +
                    ", isbn='" + isbn + '\'' +
                    ", title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", price=" + price +
                    ", cover=" + coverDto +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BookDto bookDto = (BookDto) o;
            return Objects.equals(id, bookDto.id)
                    && Objects.equals(isbn, bookDto.isbn)
                    && Objects.equals(title, bookDto.title)
                    && Objects.equals(author, bookDto.author)
                    && Objects.equals(price, bookDto.price)
                    && coverDto == bookDto.coverDto;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, isbn, title, author, price, coverDto);
        }
}
