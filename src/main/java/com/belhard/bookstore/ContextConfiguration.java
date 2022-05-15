package com.belhard.bookstore;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.impl.BookDaoJdbcImpl;
import com.belhard.bookstore.dao.impl.UserDaoJdbcImpl;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfiguration {

    @Bean
    public BookDao bookDao() {
        return new BookDaoJdbcImpl();
    }

    @Bean
    public BookService bookService() {
        return new BookServiceImpl(bookDao());
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoJdbcImpl();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userDao());
    }

}
