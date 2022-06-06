package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.dao.beans.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends Service<UserDto, Long> {

    UserDto getByEmail(String email);

    Page<UserDto> getByLastName(String lastName, Pageable pageable);

    Page<UserDto> getAll(Pageable pageable);

    UserDto save(UserDto userDto);

    Long countAll();

    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);

}
