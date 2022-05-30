package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.dao.beans.Book;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    BookDto getById(Long id);

    BookDto getByIsbn(String isbn);

    List<BookDto> getByAuthor(String author);

    BookDto create(BookDto bookDto);

    BookDto update(BookDto bookDto);

    void delete(Long id);

    Long countAll();

    Book dtoToBook(BookDto bookDto);

    BookDto bookToDto(Book book);

    BigDecimal countPriceOfAllBooksByAuthor(String author);
}

