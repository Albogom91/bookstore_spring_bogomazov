package com.belhard.bookstore.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import com.belhard.bookstore.dao.beans.Book;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPrice(resultSet.getBigDecimal("price"));
        book.setCover(Book.Cover.valueOf(resultSet.getString("cover")));
        return book;
    }
}
