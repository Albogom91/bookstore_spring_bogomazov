package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.beans.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository("userDao")
public class UserDaoJdbcImpl implements UserDao {
    private static Logger logger = LogManager.getLogger(UserDaoJdbcImpl.class);
    private static final String GET_ALL = "SELECT u.id, u.firstname, u.lastname, u.email, u.userpassword, r.name AS role " +
            "FROM users u JOIN roles r ON u.role_id = r.id WHERE deleted = false";
    private static final String GET_BY_ID = "SELECT u.id, u.firstname, u.lastname, u.email, u.userpassword, r.name AS role " +
            "FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ? AND deleted = false";
    private static final String GET_BY_EMAIL = "SELECT u.id, u.firstname, u.lastname, u.email, u.userpassword, r.name AS role " +
            "FROM users u JOIN roles r ON u.role_id = r.id WHERE LOWER(u.email) = ? AND deleted = false";
    private static final String GET_BY_LASTNAME = "SELECT u.id, u.firstname, u.lastname, u.email, u.userpassword, r.name AS role " +
            "FROM users u JOIN roles r ON u.role_id = r.id WHERE UPPER(u.lastname) = ? AND deleted = false";
    private static final String CREATE = "INSERT INTO users (firstname, lastname, email, userpassword, role_id) " +
            "VALUES (?, ?, ?, ?, (SELECT id FROM roles WHERE name = ?))";
    private static final String UPDATE = "UPDATE users SET firstname = ?, lastname = ?, email = ?, userpassword = ?, " +
            "role_id = (SELECT id FROM roles WHERE name = ?) WHERE id = ? AND deleted = false";
    private static final String DELETE = "UPDATE users SET deleted = true WHERE id = ? AND deleted = false";
    private static final String COUNT_ALL_USERS = "SELECT COUNT(*) AS count FROM users WHERE deleted = false";

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public UserDaoJdbcImpl(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(GET_ALL, userRowMapper);
    }

    @Override
    public User getUserById(Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, userRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_EMAIL, userRowMapper, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> getUsersByLastName(String lastName) {
        return jdbcTemplate.query(GET_BY_LASTNAME, userRowMapper, lastName);
    }

    @Override
    public User createUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((connection) -> getPreparedStatementForCreate(user, connection, CREATE), keyHolder);
        Number number = keyHolder.getKey();
        Long id = number.longValue();
        return getUserById(id);
    }

    private PreparedStatement getPreparedStatementForCreate(User user, Connection connection, String action) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(action, new String[]{"id"});
        prepareStatement(user, preparedStatement);
        return preparedStatement;
    }

    private void prepareStatement(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getRole().toString());
    }

    @Override
    public User updateUser(User user) {
        jdbcTemplate.update((connection) -> getPreparedStatementForUpdate(user, connection, UPDATE));
        return getUserById(user.getId());
    }

    private PreparedStatement getPreparedStatementForUpdate(User user, Connection connection, String action) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(action);
        prepareStatement(user, preparedStatement);
        preparedStatement.setLong(6, user.getId());
        return preparedStatement;
    }

    @Override
    public boolean deleteUser(Long id) {
        int result = jdbcTemplate.update((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            return preparedStatement;
        });
        return result == 1;
    }

    @Override
    public int countAllUsers() {
        int counter = 0;
        counter = jdbcTemplate.queryForObject(COUNT_ALL_USERS, Integer.class);
        return counter;
    }

}
