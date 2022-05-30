package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.beans.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository("userDao")
@Transactional
public class UserDaoJdbcImpl implements UserDao {
    private static Logger logger = LogManager.getLogger(UserDaoJdbcImpl.class);

    private static final String GET_ALL_USERS = "from User where deleted = false";
    private static final String DELETE = "update User set deleted = true where id = ?1 and deleted = false";
    private static final String COUNT_ALL_USERS = "select count(u) from User u where deleted = false";
    private static final String GET_BY_EMAIL = "select u from User u where u.email = ?1 and deleted = false";
    private static final String GET_BY_LASTNAME = "select u from User u where upper(u.lastname) = ?1 and deleted = false";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserDaoJdbcImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> getAllUsers() {
        logger.debug("Database was accessed!");
        List<User> users = entityManager.createQuery(GET_ALL_USERS, User.class).getResultList();
        return users;
    }

    @Override
    public User getUserById(Long id) {
        try {
            logger.debug("Database was accessed!");
            User user = entityManager.find(User.class, id);
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            logger.debug("Database was accessed!");
            User user = (User) entityManager.createQuery(GET_BY_EMAIL)
                    .setParameter(1, email)
                    .getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public List<User> getUsersByLastName(String lastName) {
        try {
            logger.debug("Database was accessed!");
            List<User> users = entityManager.createQuery(GET_BY_LASTNAME)
                    .setParameter(1, lastName)
                    .getResultList();
            return users;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User createUser(User user) {
        logger.debug("Database was accessed!");
        entityManager.persist(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        logger.debug("Database was accessed!");
        entityManager.merge(user);
        return user;
    }

    @Override
    public boolean deleteUser(Long id) {
        logger.debug("Database was accessed!");
        int row = entityManager.createQuery(DELETE)
                .setParameter(1, id)
                .executeUpdate();
        return row == 1;
    }

    @Override
    public Long countAllUsers() {
        logger.debug("Database was accessed!");
        Long counter = (Long) entityManager.createQuery(COUNT_ALL_USERS).getSingleResult();
        return counter;
    }

}
