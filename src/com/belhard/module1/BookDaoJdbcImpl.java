package com.belhard.module1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJdbcImpl implements BookDao{

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book getBookById(Long id) {
        Book book = null;
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement("SELECT * FROM books WHERE id=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO books (isbn, title, author)\n" +
                    "VALUES (?, ?, ?)");
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.executeUpdate();
            Statement statementQuery = connection.createStatement();
            ResultSet resultSet = statementQuery.executeQuery("SELECT * FROM books WHERE id=(SELECT max(id) FROM books)");
            while (resultSet.next()) {
                book.setId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        try {
            Connection connection = DbConfigurator.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO books (isbn, title, author)\n" +
                    "VALUES (?, ?, ?)");
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.executeUpdate();
            Statement statementQuery = connection.createStatement();
            ResultSet resultSet = statementQuery.executeQuery("SELECT * FROM books WHERE id=(SELECT max(id) FROM books)");
            while (resultSet.next()) {
                book.setId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public void deleteBook(Long id) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement("UPDATE books SET deleted = true WHERE id=?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countAllBooks() {
        int counter = 0;
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM books");
            if(resultSet.next()){
                counter = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public Book getBookByIsbn(String isbn) {
        Book book = null;
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement("SELECT * FROM books WHERE isbn=?");
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public List<Book> getBooksByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement("SELECT * FROM books WHERE author=?");
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
