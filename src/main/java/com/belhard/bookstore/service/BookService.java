package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.dao.beans.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BookService extends Service<BookDto, Long> {

    BookDto getByIsbn(String isbn);

    Page<BookDto> getByAuthor(String author, Pageable pageable);

    Page<BookDto> getAll(Pageable pageable);

    BookDto save(BookDto bookDto);

    Long countAll();

    Book dtoToBook(BookDto bookDto);

    BookDto bookToDto(Book book);

}

