package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.beans.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("bookDao")
public class BookDaoJdbcImpl implements BookDao {
    private static Logger logger = LogManager.getLogger(BookDaoJdbcImpl.class);
    private static final String GET_ALL_BOOKS = "from Book where deleted = false";
    private static final String DELETE = "update Book set deleted = true where id = ?1 and deleted = false";
    private static final String COUNT_ALL_BOOKS = "select count(b) from Book b where deleted = false";
    private static final String GET_BY_ISBN = "select b from Book b where b.isbn = ?1 and deleted = false";
    private static final String GET_BY_AUTHOR = "select b from Book b where upper(b.author) = ?1 and deleted = false";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> getAll() {
        logger.debug("Database was accessed!");
        List<Book> books = entityManager.createQuery(GET_ALL_BOOKS, Book.class).getResultList();
        return books;
    }

    @Override
    public Book getById(Long id) {
        try {
            logger.debug("Database was accessed!");
            Book book = entityManager.find(Book.class, id);
            return book;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Book create(Book book) {
        logger.debug("Database was accessed!");
        entityManager.persist(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        logger.debug("Database was accessed!");
        entityManager.merge(book);
        return book;
    }

    @Override
    public boolean delete(Long id) {
        logger.debug("Database was accessed!");
        int row = entityManager.createQuery(DELETE)
                .setParameter(1, id)
                .executeUpdate();
        return row == 1;
    }

    @Override
    public Long countAllBooks() {
        logger.debug("Database was accessed!");
        Long counter = (Long) entityManager.createQuery(COUNT_ALL_BOOKS).getSingleResult();
        return counter;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        try {
            logger.debug("Database was accessed!");
            Book book = (Book) entityManager.createQuery(GET_BY_ISBN)
                    .setParameter(1, isbn)
                    .getSingleResult();
            return book;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        try {
            logger.debug("Database was accessed!");
            List<Book> books = entityManager.createQuery(GET_BY_AUTHOR)
                    .setParameter(1, author)
                    .getResultList();
            return books;
        } catch (NoResultException e) {
            return null;
        }
    }
}

