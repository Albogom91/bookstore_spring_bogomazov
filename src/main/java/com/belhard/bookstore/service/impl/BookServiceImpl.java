package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.beans.Book;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static Logger logger = LogManager.getLogger(BookServiceImpl.class);
    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<BookDto> getAll() {
        logger.debug("Service method \"getAll\" was called.");
        List<Book> books = bookDao.getAllBooks();
        return booksToBooksDtos(books);
    }

    private BookDto bookToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());
        bookDto.setCoverDto(BookDto.CoverDto.valueOf(book.getCover().toString()));
        return bookDto;
    }

    private List<BookDto> booksToBooksDtos(List<Book> books) {
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = bookToDto(book);
            bookDtos.add(bookDto);
        }
        return bookDtos;
    }

    @Override
    public BookDto getById(Long id) {
        logger.debug("Service method \"getById\" was called.");
        Book book = bookDao.getBookById(id);
        if (book == null) {
            logger.error("There is no book with such id: " + id);
            throw new RuntimeException("There is no book with such id: " + id);
        }
        return bookToDto(book);
    }

    @Override
    public BookDto getByIsbn(String isbn) {
        logger.debug("Service method \"getByIsbn\" was called.");
        Book book = bookDao.getBookByIsbn(isbn);
        if (book == null) {
            logger.error("There is no book with such isbn: " + isbn);
            throw new RuntimeException("There is no book with such isbn: " + isbn);
        }
        return bookToDto(book);
    }

    @Override
    public List<BookDto> getByAuthor(String author) {
        logger.debug("Service method \"getByAuthor\" was called.");
        List<Book> books = bookDao.getBooksByAuthor(author);
        if (books.isEmpty()) {
            logger.error("There are no books by such author: " + author);
            throw new RuntimeException("There are no books by such author: " + author);
        }
        return booksToBooksDtos(books);
    }

    @Override
    public BookDto create(BookDto bookDto) {
        logger.debug("Service method \"create\" was called.");
        Book checkBook = bookDao.getBookByIsbn(bookDto.getIsbn());
        if (checkBook != null) {
            logger.error("Book with such isbn already exists: " + checkBook.getIsbn());
            throw new RuntimeException("Book with such isbn already exists: " + checkBook.getIsbn());
        }
        Book book = dtoToBook(bookDto);
        book = bookDao.createBook(book);
        return bookToDto(book);
    }

    private Book dtoToBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());
        book.setCover(Book.Cover.valueOf(bookDto.getCoverDto().toString()));
        return book;
    }

    @Override
    public BookDto update(BookDto bookDto) {
        logger.debug("Service method \"update\" was called.");
        Book checkBook = bookDao.getBookByIsbn(bookDto.getIsbn());
        if (checkBook != null && checkBook.getId() != bookDto.getId()) {
            logger.error("Book with such ISBN already exists!" + checkBook.getIsbn());
            throw new RuntimeException("Book with such ISBN already exists!" + checkBook.getIsbn());
        }
        Book book = dtoToBook(bookDto);
        book = bookDao.updateBook(book);
        return bookToDto(book);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Service method \"delete\" was called.");
        if (!bookDao.deleteBook(id)) {
            logger.error("There is no book to delete with such id: " + id);
            throw new RuntimeException("There is no book to delete with such id: " + id);
        }
    }

    @Override
    public int countAll() {
        logger.debug("Service method \"countAll\" was called.");
        return bookDao.countAllBooks();
    }

    public BigDecimal countPriceOfAllBooksByAuthor(String author) {
        logger.debug("Service method \"countPriceOfAllBooksByAuthor\" was called.");
        BigDecimal sumPrice = new BigDecimal(0.0);
        List<BookDto> bookDtos = getByAuthor(author);
        for (BookDto bookDto : bookDtos) {
            sumPrice = sumPrice.add(bookDto.getPrice());
        }
        return sumPrice;
    }
}

