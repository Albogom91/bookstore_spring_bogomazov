package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.beans.User;

import java.util.List;

public interface UserDao extends EntityDao<User, Long> {

    User getUserByEmail(String email);

    List<User> getUsersByLastName(String lastName);

    Long countAllUsers();
}
