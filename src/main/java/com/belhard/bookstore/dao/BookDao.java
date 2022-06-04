package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.beans.Book;

import java.util.List;

public interface BookDao extends EntityDao<Book, Long> {

    Book getBookByIsbn(String isbn);

    List<Book> getBooksByAuthor(String author);

    Long countAllBooks();
}

