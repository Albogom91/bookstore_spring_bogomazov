package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.BookRepository;
import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.UserRepository;
import com.belhard.bookstore.dao.beans.Book;
import com.belhard.bookstore.dao.beans.User;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserDao userDao;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> getAll() {
        logger.debug("Service method \"getAll\" was called.");

        return userRepository.findAll().stream().map(this::userToDto).toList();

        //return userDao.getAll().stream().map(this::userToDto).toList();
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {

        return userRepository.findByDeleted(Boolean.FALSE, pageable).map(this::userToDto);

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

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is no user with such id: " + id);
                    return new RuntimeException("There is no user with such id: " + id);
                });

        return userToDto(user);
    }

    @Override
    public UserDto getByEmail(String email) {
        logger.debug("Service method \"getByEmail\" was called.");
        User user = userRepository.findByEmail(email);

        if (user == null) {
            logger.error("There is no user with such email: " + email);
            throw new RuntimeException("There is no user with such email: " + email);
        }
        return userToDto(user);
    }

    @Override
    public Page<UserDto> getByLastName(String lastName, Pageable pageable) {
        logger.debug("Service method \"getByLastName\" was called.");
        Page<User> users = userRepository.findByDeletedAndLastNameIgnoreCase(Boolean.FALSE, lastName, pageable);
        if (users.isEmpty()) {
            logger.error("There are no users such last name: " + lastName);
            throw new RuntimeException("There are no users such last name: " + lastName);
        }
        return users.map(this::userToDto);

        /*List<User> users = userDao.getUsersByLastName(lastName);
        if (users.isEmpty()) {
            logger.error("There are no users with such last name: " + lastName);
        }
        return usersToUsersDtos(users);*/
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        logger.debug("Service method \"create\" was called.");
        User checkUser = userDao.getUserByEmail(userDto.getEmail());
        if (checkUser != null) {
            logger.error("User with such email already exists: " + checkUser.getEmail());
            throw new RuntimeException("User with such email already exists: " + checkUser.getEmail());
        }
        User user = dtoToUser(userDto);
        //user = userDao.create(user);
        user = userRepository.save(user);
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
            throw new RuntimeException("User with such email already exists: " + checkUser.getEmail());
        }
        User user = dtoToUser(userDto);
        //user = userDao.update(user);
        user = userRepository.save(user);
        return userToDto(user);
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        logger.debug("Service method \"save\" was called.");
        User checkUser = userRepository.findByEmail(userDto.getEmail());
        if (checkUser != null && checkUser.getId() != userDto.getId()) {
            logger.error("User with such email already exists: " + checkUser.getEmail());
            throw new RuntimeException("User with such email already exists: " + checkUser.getEmail());
        }
        User user = dtoToUser(userDto);
        user = userRepository.save(user);
        return userToDto(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logger.debug("Service method \"delete\" was called.");
        userRepository.delete(id);
    }

    @Override
    public Long countAll() {
        logger.debug("Service method \"countAll\" was called.");
        return userRepository.count();
    }

}
