package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.dao.beans.User;

import java.util.List;

public interface UserService extends Service<UserDto, Long> {

    UserDto getByEmail(String email);

    List<UserDto> getByLastName(String lastName);

    Long countAll();

    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);

    boolean validate(String email, String password);
}
