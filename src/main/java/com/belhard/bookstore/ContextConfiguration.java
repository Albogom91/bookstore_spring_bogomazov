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
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@EntityScan(basePackages = {"com.belhard.bookstore.*"})
public class ContextConfiguration {
    private static OrderService orderService;
    private static UserService userService;
    private static BookService bookService;


    @Autowired
    public ContextConfiguration(OrderService orderService, UserService userService, BookService bookService) {
        this.orderService = orderService;
        this.userService = userService;
        this.bookService = bookService;
    }


    public static void main(String[] args) {

        SpringApplication.run(ContextConfiguration.class, args);

        /*UserDto user = userService.getById(3L);
        UserDto user1 = userService.getById(8L);
        BookDto book = bookService.getById(7L);
        BookDto book1 = bookService.getById(8L);
        BookDto book2 = bookService.getById(9L);

        OrderItemDto oi = new OrderItemDto(book, 2, book.getPrice());
        OrderItemDto oi1 = new OrderItemDto(book1, 1, book1.getPrice());
        OrderItemDto oi2 = new OrderItemDto(book2, 1, book2.getPrice());

        List<OrderItemDto> ois = new ArrayList<>();
        ois.add(oi);
        ois.add(oi1);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-DD HH:MM:SS");

        OrderDto order = new OrderDto(user, LocalDateTime.now(), OrderDto.StatusDto.PENDING, ois);
        System.out.println(order);
        order = orderService.create(order);
        System.out.println(order);*/

        //entityManager.detach(order);

        /*OrderDto order = orderService.getById(9L);
        UserDto user1 = userService.getById(9L);
        BookDto book = bookService.getById(7L);
        BookDto book1 = bookService.getById(8L);
        BookDto book2 = bookService.getById(9L);
        OrderItemDto oi = new OrderItemDto(book, 2, book.getPrice());
        OrderItemDto oi1 = new OrderItemDto(book1, 1, book1.getPrice());
        OrderItemDto oi2 = new OrderItemDto(book2, 1, book2.getPrice());*/

        /*order.setUserDto(user1);
        ois.remove(oi1);
        ois.add(oi2);

        order.setItems(ois);
        System.out.println(order);
        order = orderService.update(order);
        System.out.println(order);*/

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

}
