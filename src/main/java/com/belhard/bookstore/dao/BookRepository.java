package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.beans.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("from Book where deleted = false")
    List<Book> findAll();

    Page<Book> findByDeleted(boolean deleted, Pageable pageable);

    Book findByIsbn(String isbn);

    Page<Book> findByDeletedAndAuthorIgnoreCase(boolean deleted, String author, Pageable pageable);

    @Modifying
    @Query("update Book set deleted = true where id = ?1 and deleted = false")
    void delete(Long id);

    @Query("select count(b) from Book b where deleted = false")
    long count();
}
