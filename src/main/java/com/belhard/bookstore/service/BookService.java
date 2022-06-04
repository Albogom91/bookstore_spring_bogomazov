package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.dao.beans.Book;

import java.math.BigDecimal;
import java.util.List;

public interface BookService extends Service<BookDto, Long> {

    BookDto getByIsbn(String isbn);

    List<BookDto> getByAuthor(String author);

    BookDto save(BookDto bookDto);

    Long countAll();

    Book dtoToBook(BookDto bookDto);

    BookDto bookToDto(Book book);

    BigDecimal countPriceOfAllBooksByAuthor(String author);
}

