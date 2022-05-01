package com.belhard.module1.service;

import com.belhard.module1.service.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    UserDto getById(Long id);

    UserDto getByEmail(String email);

    List<UserDto> getByLastName(String lastName);

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void delete(Long id);

    int countAll();

    boolean validate(String email, String password);
}
