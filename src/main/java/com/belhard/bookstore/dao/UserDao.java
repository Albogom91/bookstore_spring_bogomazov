package main.java.com.belhard.bookstore.dao;

import main.java.com.belhard.bookstore.dao.beans.User;
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
