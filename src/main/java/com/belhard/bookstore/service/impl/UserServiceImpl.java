package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.beans.User;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> getAll() {
        logger.debug("Service method \"getAll\" was called.");
        return userDao.getAll().stream().map(this::userToDto).toList();
    }

    @Override
    public UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoleDto(UserDto.RoleDto.valueOf(user.getRole().toString()));
        return userDto;
    }

    private List<UserDto> usersToUsersDtos(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(userToDto(user));
        }
        return userDtos;
    }

    @Override
    public UserDto getById(Long id) {
        logger.debug("Service method \"getById\" was called.");
        User user = userDao.getById(id);
        if (user == null) {
            logger.error("There is no user with such id: " + id);
        }
        return userToDto(user);
    }

    @Override
    public UserDto getByEmail(String email) {
        logger.debug("Service method \"getByEmail\" was called.");
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            logger.error("There is no user with such email: " + email);
        }
        return userToDto(user);
    }

    @Override
    public List<UserDto> getByLastName(String lastName) {
        logger.debug("Service method \"getByLastName\" was called.");
        List<User> users = userDao.getUsersByLastName(lastName);
        if (users.isEmpty()) {
            logger.error("There are no users with such last name: " + lastName);
        }
        return usersToUsersDtos(users);
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        logger.debug("Service method \"create\" was called.");
        User checkUser = userDao.getUserByEmail(userDto.getEmail());
        if (checkUser != null) {
            logger.error("User with such email already exists: " + checkUser.getEmail());
        }
        User user = dtoToUser(userDto);
        user = userDao.create(user);
        return userToDto(user);
    }

    @Override
    public User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(User.Role.valueOf(userDto.getRoleDto().toString()));
        return user;
    }

    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        logger.debug("Service method \"update\" was called.");
        User checkUser = userDao.getUserByEmail(userDto.getEmail());
        if (checkUser != null && checkUser.getId() != userDto.getId()) {
            logger.error("User with such email already exists: " + checkUser.getEmail());
        }
        User user = dtoToUser(userDto);
        user = userDao.update(user);
        return userToDto(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logger.debug("Service method \"delete\" was called.");
        if (!userDao.delete(id)) {
            logger.error("There is no user to delete with such id: " + id);
        }
    }

    @Override
    public Long countAll() {
        logger.debug("Service method \"countAll\" was called.");
        return userDao.countAllUsers();
    }

    @Override
    public boolean validate(String email, String password) {
        logger.debug("Service method \"validate\" was called.");
        User user = userDao.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
