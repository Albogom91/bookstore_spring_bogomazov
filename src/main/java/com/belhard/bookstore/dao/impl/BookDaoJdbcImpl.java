package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.beans.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDaoJdbcImpl implements BookDao {
    private static Logger logger = LogManager.getLogger(BookDaoJdbcImpl.class);
    private static final String GET_ALL = "SELECT b.id, b.isbn, b.title, b.author, b.price, c.name AS cover " +
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE deleted = false";
    private static final String GET_BY_ID = "SELECT b.id, b.isbn, b.title, b.author, b.price, c.name AS cover " +
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.id = ? AND deleted = false";
    private static final String CREATE = "INSERT INTO books (isbn, title, author, price, cover_id) " +
            "VALUES (?, ?, ?, ?, (SELECT id FROM covers WHERE name = ?))";
    private static final String UPDATE = "UPDATE books SET isbn = ?, title = ?, author = ?, price = ?, cover_id = (SELECT id FROM covers WHERE name = ?) " +
            "WHERE id = ? AND deleted = false";
    private static final String DELETE = "UPDATE books SET deleted = true WHERE id = ? AND deleted = false";
    private static final String COUNT_ALL_BOOKS = "SELECT COUNT(*) AS count FROM books WHERE deleted = false";
    private static final String GET_BY_ISBN = "SELECT b.id, b.isbn, b.title, b.author, b.price, c.name AS cover " +
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.isbn = ? AND deleted = false";
    private static final String GET_BY_AUTHOR = "SELECT b.id, b.isbn, b.title, b.author, b.price, c.name AS cover " +
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE UPPER(b.author) = ? AND deleted = false";

    private final JdbcTemplate jdbcTemplate;
    private final BookRowMapper bookRowMapper;

    @Autowired
    public BookDaoJdbcImpl(JdbcTemplate jdbcTemplate, BookRowMapper bookRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookRowMapper = bookRowMapper;
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query(GET_ALL, bookRowMapper);
    }

    @Override
    public Book getBookById(Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, bookRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Book createBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, new String[]{"id"});
            prepareStatement(book, preparedStatement);
            return preparedStatement;
        }, keyHolder);
        Number number = keyHolder.getKey();
        Long id = number.longValue();
        return getBookById(id);
    }

    private void prepareStatement(Book book, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, book.getIsbn());
        preparedStatement.setString(2, book.getTitle());
        preparedStatement.setString(3, book.getAuthor());
        preparedStatement.setBigDecimal(4, book.getPrice());
        preparedStatement.setString(5, book.getCover().toString());
    }

    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            prepareStatement(book, preparedStatement);
            preparedStatement.setLong(6, book.getId());
            return preparedStatement;
        });
        return getBookById(book.getId());
    }

    @Override
    public boolean deleteBook(Long id) {
        int result = jdbcTemplate.update((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            return preparedStatement;
        });
        return result == 1;
    }

    @Override
    public int countAllBooks() {
        int counter = 0;
        counter = jdbcTemplate.queryForObject(COUNT_ALL_BOOKS, Integer.class);
        return counter;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ISBN, bookRowMapper, isbn);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return jdbcTemplate.query(GET_BY_AUTHOR, bookRowMapper, author);
    }
}

