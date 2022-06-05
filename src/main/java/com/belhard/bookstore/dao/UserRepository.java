package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.beans.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User where deleted = false")
    List<User> findAll();

    Page<User> findByDeleted(boolean deleted, Pageable pageable);

    User findByEmail(String isbn);

    Page<User> findByDeletedAndLastNameIgnoreCase(boolean deleted, String lastName, Pageable pageable);

    @Modifying
    @Query("update User set deleted = true where id = ?1 and deleted = false")
    void delete(Long id);

    @Query("select count(u) from User u where deleted = false")
    long count();
}
