package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.beans.User;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserDao userDao;

    public UserServiceImpl() {

    }

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> getAll() {
        logger.debug("Service method \"getAll\" was called.");
        List<User> users = userDao.getAllUsers();
        return usersToUsersDtos(users);
    }

    private UserDto userToDto(User user) {
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
        User user = userDao.getUserById(id);
        if (user == null) {
            logger.error("There is no user with such id: " + id);
            //throw new RuntimeException("There is no user with such id: " + id);
            return null;
        }
        return userToDto(user);
    }

    @Override
    public UserDto getByEmail(String email) {
        logger.debug("Service method \"getByEmail\" was called.");
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            logger.error("There is no user with such email: " + email);
            throw new RuntimeException("There is no user with such email!");
        }
        return userToDto(user);
    }

    @Override
    public List<UserDto> getByLastName (String lastName) {
        logger.debug("Service method \"getByLastName\" was called.");
        List<User> users = userDao.getUsersByLastName(lastName);
        if (users.isEmpty()) {
            logger.error("There are no users with such last name: " + lastName);
            throw new RuntimeException("There are no users with such lastname!");
        }
        return usersToUsersDtos(users);
    }

    @Override
    public UserDto create(UserDto userDto) {
        logger.debug("Service method \"create\" was called.");
        User checkUser = userDao.getUserByEmail(userDto.getEmail());
        if (checkUser != null) {
            logger.error("User with such email already exists: " + checkUser.getEmail());
            throw new RuntimeException("User with such email already exists!");
        }
        User user = dtoToUser(userDto);
        user = userDao.createUser(user);
        return userToDto(user);
    }

    private User dtoToUser(UserDto userDto) {
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
    public UserDto update(UserDto userDto) {
        logger.debug("Service method \"update\" was called.");
        User checkUser = userDao.getUserByEmail(userDto.getEmail());
        if (checkUser != null && checkUser.getId() != userDto.getId()) {
            logger.error("User with such email already exists: " + checkUser.getEmail());
            throw new RuntimeException("User with such email already exists!");
        }
        User user = dtoToUser(userDto);
        user = userDao.updateUser(user);
        return userToDto(user);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Service method \"delete\" was called.");
        if (!userDao.deleteUser(id)) {
            logger.error("There is no user to delete with such id: " + id);
            throw new RuntimeException("Deletion was not completed! There is no user with such id!");
        }
    }

    @Override
    public int countAll() {
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
