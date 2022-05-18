package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.dbconfig.DbConfigurator;
import com.belhard.bookstore.dao.beans.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.author = ? AND deleted = false";
    private JdbcTemplate jdbcTemplate;
    private BookRowMapper bookRowMapper;

    public BookDaoJdbcImpl() {

    }

    @Autowired
    public BookDaoJdbcImpl(JdbcTemplate jdbcTemplate, BookRowMapper bookRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookRowMapper = bookRowMapper;
    }

    /*@Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                books.add(processResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("There was an error in the process of acquiring list of all books!");
        }
        return books;
    }*/

    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query(GET_ALL, bookRowMapper);
    }

    @Override
    public Book getBookById(Long id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, bookRowMapper, id);
    }

    private Book processResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPrice(resultSet.getBigDecimal("price"));
        book.setCover(Book.Cover.valueOf(resultSet.getString("cover")));
        return book;
    }

    /*@Override
    public Book getBookById(Long id) {
        Book book = null;
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(GET_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = processResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("There was an error while acquiring book by id: " + id);
        }
        return book;
    }*/

    @Override
    public Book createBook(Book book) {
        try {
            Connection connection = DbConfigurator.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            prepareStatement(book, statement);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong("id");
                return getBookById(id);
            }
        } catch (SQLException e) {
            logger.error("There was an error while creating new book!");
        }
        throw new RuntimeException("Book was not created!");
    }

    private void prepareStatement(Book book, PreparedStatement statement) throws SQLException {
        statement.setString(1, book.getIsbn());
        statement.setString(2, book.getTitle());
        statement.setString(3, book.getAuthor());
        statement.setBigDecimal(4, book.getPrice());
        statement.setString(5, book.getCover().toString());
    }

    @Override
    public Book updateBook(Book book) {
        try {
            Connection connection = DbConfigurator.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            prepareStatement(book, statement);
            statement.setLong(6, book.getId());
            int result = statement.executeUpdate();
            if (result == 1) {
                return getBookById(book.getId());
            }
        } catch (SQLException e) {
            logger.error("There was an error while updating book with id: " + book.getId());
        }
        throw new RuntimeException("Book was not updated! Book id: " + book.getId());
    }

    @Override
    public boolean deleteBook(Long id) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(DELETE);
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            logger.error("There was an error while deleting book with id: " + id);
        }
        throw new RuntimeException("There was a mistake during deletion process!");
    }

    @Override
    public int countAllBooks() {
        int counter = 0;
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_ALL_BOOKS);
            if (resultSet.next()) {
                counter = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            logger.error("There was an error while counting books!");
        }
        return counter;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        Book book = null;
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(GET_BY_ISBN);
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = processResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("There was an error while acquiring book by isbn: " + isbn);
        }
        return book;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(GET_BY_AUTHOR);
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(processResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("There was an error while acquiring book by author: " + author);
        }
        return books;
    }
}

