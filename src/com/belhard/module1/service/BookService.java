package com.belhard.module1.service;

import com.belhard.module1.service.dto.BookDto;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    BookDto getBookByIsbn(String isbn);

    List<BookDto> getBooksByAuthor(String author);

    BookDto createBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    void deleteBook(Long id);

    int countAllBooks();

    public BigDecimal countPriceOfAllBooksByAuthor(String author);
}
