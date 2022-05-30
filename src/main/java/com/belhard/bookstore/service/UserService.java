package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.dao.beans.User;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    UserDto getById(Long id);

    UserDto getByEmail(String email);

    List<UserDto> getByLastName(String lastName);

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void delete(Long id);

    Long countAll();

    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);

    boolean validate(String email, String password);
}
