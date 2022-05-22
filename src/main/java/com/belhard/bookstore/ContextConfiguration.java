package com.belhard.bookstore;

import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContextConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(ContextConfiguration.class, args);

    }

}
