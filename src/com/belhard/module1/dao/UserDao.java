package com.belhard.module1.dao;

import com.belhard.module1.dao.beans.User;
import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    List<User> getUsersByLastName(String lastName);

    User createUser(User user);

    User updateUser(User user);

    boolean deleteUser(Long id);

    int countAllUsers();
}
