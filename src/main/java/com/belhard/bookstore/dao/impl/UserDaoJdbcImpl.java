package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.beans.User;
import com.belhard.bookstore.dao.dbconfig.DbConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository("userDao")
public class UserDaoJdbcImpl implements UserDao {
    private static Logger logger = LogManager.getLogger(UserDaoJdbcImpl.class);
    private static final String GET_ALL = "SELECT u.id, u.firstname, u.lastname, u.email, u.userpassword, r.name AS role " +
            "FROM users u JOIN roles r ON u.role_id = r.id WHERE deleted = false";
    private static final String GET_BY_ID = "SELECT u.id, u.firstname, u.lastname, u.email, u.userpassword, r.name AS role " +
            "FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ? AND deleted = false";
    private static final String GET_BY_EMAIL = "SELECT u.id, u.firstname, u.lastname, u.email, u.userpassword, r.name AS role " +
            "FROM users u JOIN roles r ON u.role_id = r.id WHERE u.email = ? AND deleted = false";
    private static final String GET_BY_LASTNAME = "SELECT u.id, u.firstname, u.lastname, u.email, u.userpassword, r.name AS role " +
            "FROM users u JOIN roles r ON u.role_id = r.id WHERE u.lastname = ? AND deleted = false";
    private static final String CREATE = "INSERT INTO users (firstname, lastname, email, userpassword, role_id) " +
            "VALUES (?, ?, ?, ?, (SELECT id FROM roles WHERE name = ?))";
    private static final String UPDATE = "UPDATE users SET firstname = ?, lastname = ?, email = ?, userpassword = ?, " +
            "role_id = (SELECT id FROM roles WHERE name = ?) WHERE id = ? AND deleted = false";
    private static final String DELETE = "UPDATE users SET deleted = true WHERE id = ? AND deleted = false";
    private static final String COUNT_ALL_USERS = "SELECT COUNT(*) AS count FROM users WHERE deleted = false";

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                users.add(processResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return users;
    }

    private User processResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("firstname"));
        user.setLastName(resultSet.getString("lastname"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("userpassword"));
        user.setRole(User.Role.valueOf(resultSet.getString("role")));
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = null;
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(GET_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = processResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(GET_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = processResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return user;
    }

    @Override
    public List<User> getUsersByLastName(String lastName) {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(GET_BY_LASTNAME);
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(processResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return users;
    }

    @Override
    public User createUser(User user) {
        try {
            Connection connection = DbConfigurator.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            prepareStatement(user, statement);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong("id");
                return getUserById(id);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        throw new RuntimeException("User was not created!");
    }

    private void prepareStatement(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getRole().toString());
    }

    @Override
    public User updateUser(User user) {
        try {
            Connection connection = DbConfigurator.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            prepareStatement(user, statement);
            statement.setLong(6, user.getId());
            int result = statement.executeUpdate();
            if (result == 1) {
                return getUserById(user.getId());
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        throw new RuntimeException("User was not updated!");
    }

    @Override
    public boolean deleteUser(Long id) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection().prepareStatement(DELETE);
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            logger.error(e);
        }
        throw new RuntimeException("There was a mistake during deletion process!");
    }

    @Override
    public int countAllUsers() {
        int counter = 0;
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_ALL_USERS);
            if (resultSet.next()) {
                counter = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return counter;
    }
}
