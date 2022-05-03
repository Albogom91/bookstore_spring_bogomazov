package main.java.com.belhard.bookstore.service.impl;

import main.java.com.belhard.bookstore.dao.UserDao;
import main.java.com.belhard.bookstore.dao.beans.User;
import main.java.com.belhard.bookstore.dao.impl.UserDaoJdbcImpl;
import main.java.com.belhard.bookstore.service.UserService;
import main.java.com.belhard.bookstore.service.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final UserDao USER_DAO = new UserDaoJdbcImpl();

    @Override
    public List<UserDto> getAll() {
        List<User> users = USER_DAO.getAllUsers();
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
        User user = USER_DAO.getUserById(id);
        if (user == null) {
            throw new RuntimeException("There is no user with such id!");
        }
        return userToDto(user);
    }

    @Override
    public UserDto getByEmail(String email) {
        User user = USER_DAO.getUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("There is no user with such email!");
        }
        return userToDto(user);
    }

    @Override
    public List<UserDto> getByLastName (String lastName) {
        List<User> users = USER_DAO.getUsersByLastName(lastName);
        if (users.isEmpty()) {
            throw new RuntimeException("There are no users with such lastname!");
        }
        return usersToUsersDtos(users);
    }

    @Override
    public UserDto create(UserDto userDto) {
        User checkUser = USER_DAO.getUserByEmail(userDto.getEmail());
        if (checkUser != null) {
            throw new RuntimeException("User with such email already exists!");
        }
        User user = dtoToUser(userDto);
        user = USER_DAO.createUser(user);
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
        User checkUser = USER_DAO.getUserByEmail(userDto.getEmail());
        if (checkUser != null && checkUser.getId() != userDto.getId()) {
            throw new RuntimeException("User with such email already exists!");
        }
        User user = dtoToUser(userDto);
        user = USER_DAO.updateUser(user);
        return userToDto(user);
    }

    @Override
    public void delete(Long id) {
        if (!USER_DAO.deleteUser(id)) {
            throw new RuntimeException("Deletion was not completed! There is no user with such id!");
        }
    }

    @Override
    public int countAll() {
        return USER_DAO.countAllUsers();
    }

    @Override
    public boolean validate(String email, String password) {
        User user = USER_DAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
