package com.belhard.module1.dao.impl;

import com.belhard.module1.dao.BookDao;
import com.belhard.module1.dao.dbconfig.DbConfigurator;
import com.belhard.module1.dao.beans.Book;
import com.belhard.module1.util.ReaderUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJdbcImpl implements BookDao {
    public static final String GET_ALL = "SELECT b.id, b.isbn, b.title, b.author, b.price, c.name AS cover " +
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE deleted = false";
    public static final String GET_BY_ID = "SELECT b.id, b.isbn, b.title, b.author, b.price, c.name AS cover " +
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.id = ? AND deleted = false";
    public static final String CREATE = "INSERT INTO books (isbn, title, author, price, cover_id) " +
            "VALUES (?, ?, ?, ?, (SELECT id FROM covers WHERE name = ?))";
    public static final String UPDATE = "UPDATE books SET isbn = ?, title = ?, author = ?, price = ?, cover_id = (SELECT id FROM covers WHERE name = ?) " +
            "WHERE id = ? AND deleted = false";
    public static final String DELETE = "UPDATE books SET deleted = true WHERE id = ? AND deleted = false";
    public static final String COUNT_ALL_BOOKS = "SELECT COUNT(*) FROM books WHERE deleted = false";
    public static final String GET_BY_ISBN = "SELECT b.id, b.isbn, b.title, b.author, b.price, c.name AS cover " +
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.isbn = ? AND deleted = false";
    public static final String GET_BY_AUTHOR = "SELECT b.id, b.isbn, b.title, b.author, b.price, c.name AS cover " +
            "FROM books b JOIN covers c ON b.cover_id = c.id WHERE b.author = ? AND deleted = false";

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                books.add(processResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    private Book processResultSet(ResultSet resultSet) {
        Book book = new Book();
        try {
            book.setId(resultSet.getLong("id"));
            book.setIsbn(resultSet.getString("isbn"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setPrice(resultSet.getBigDecimal("price"));
            book.setCover(Book.Cover.valueOf(resultSet.getString("cover")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
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
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public Book createBook(Book book) {
        try {
            Connection connection = DbConfigurator.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setBigDecimal(4, book.getPrice());
            statement.setString(5, book.getCover().toString());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong("id");
                return getBookById(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Book was not created!");
    }

    @Override
    public Book updateBook(Book book) {
        try {
            Connection connection = DbConfigurator.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setBigDecimal(4, book.getPrice());
            statement.setString(5, book.getCover().toString());
            statement.setLong(6, book.getId());
            int result = statement.executeUpdate();
            if (result == 1) {
                return getBookById(book.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Book was not updated!");
    }

    @Override
    public boolean deleteBook(Long id) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(DELETE);
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return books;
    }
}

