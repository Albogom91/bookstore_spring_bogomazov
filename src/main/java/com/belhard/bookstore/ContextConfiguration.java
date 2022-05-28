package com.belhard.bookstore;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.beans.Book;
import com.belhard.bookstore.dao.impl.BookDaoJdbcImpl;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ContextConfiguration {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MyPU");
    //EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static void main(String[] args) {
        SpringApplication.run(ContextConfiguration.class, args);
        //init();
        //Book book = bookDao.getBookById(2L);
        //System.out.println(book);

        /*Book book = new Book("13", "Y", "Y", BigDecimal.valueOf(5.0), Book.Cover.HARD);
        System.out.println(book);
        book = bookDao.createBook(book);
        System.out.println(book);*/

        /*Book book = bookDao.getBookById(70L);
        System.out.println(book);
        book.setAuthor("ZZ");
        book.setCover(Book.Cover.SPECIAL);
        book = bookDao.updateBook(book);
        System.out.println(book);*/

        /*List<Book> books = bookDao.getAllBooks();
        books.forEach(System.out::println);*/


    }

    /*private static void init() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MyPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        bookDao = new BookDaoJdbcImpl(entityManager);
    }*/

}
