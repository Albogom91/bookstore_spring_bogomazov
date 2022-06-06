package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.BookDao;
import com.belhard.bookstore.dao.BookRepository;
import com.belhard.bookstore.dao.beans.Book;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static Logger logger = LogManager.getLogger(BookServiceImpl.class);
    private final BookDao bookDao;
    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<BookDto> getAll() {
        logger.debug("Service method \"getAll\" was called.");

        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::bookToDto).toList();

    }

    @Override
    public Page<BookDto> getAll(Pageable pageable) {

        return bookRepository.findByDeleted(Boolean.FALSE, pageable).map(this::bookToDto);

    }

    @Override
    public BookDto bookToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());
        bookDto.setCoverDto(BookDto.CoverDto.valueOf(book.getCover().toString()));
        return bookDto;
    }

    @Override
    public BookDto getById(Long id) {
        logger.debug("Service method \"getById\" was called.");

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is no book with such id: " + id);
                    return new RuntimeException("There is no book with such id: " + id);
                });
        return bookToDto(book);
    }

    @Override
    public BookDto getByIsbn(String isbn) {
        logger.debug("Service method \"getByIsbn\" was called.");

        Book book = bookRepository.findByIsbn(isbn);

        if (book == null) {
            logger.error("There is no book with such isbn: " + isbn);
            throw new RuntimeException("There is no book with such id: " + isbn);
        }
        return bookToDto(book);
    }


    @Override
    public Page<BookDto> getByAuthor(String author, Pageable pageable) {
        logger.debug("Service method \"getByAuthor\" was called.");
        Page<Book> books = bookRepository.findByDeletedAndAuthorIgnoreCase(Boolean.FALSE, author, pageable);
        if (books.isEmpty()) {
            logger.error("There are no books by such author: " + author);
            throw new RuntimeException("There are no books by such author: " + author);
        }
        return books.map(this::bookToDto);
    }

    @Override
    @Transactional
    public BookDto create(BookDto bookDto) {
        logger.debug("Service method \"create\" was called.");
        Book checkBook = bookRepository.findByIsbn(bookDto.getIsbn());
        if (checkBook != null) {
            logger.error("Book with such ISBN already exists: " + checkBook.getIsbn());
            throw new RuntimeException("Book with such ISBN already exists: " + checkBook.getIsbn());
        }
        Book book = dtoToBook(bookDto);
        //book = bookDao.create(book);
        book = bookRepository.save(book);
        return bookToDto(book);
    }

    @Override
    public Book dtoToBook(BookDto bookDto) {
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
    @Transactional
    public BookDto update(BookDto bookDto) {
        logger.debug("Service method \"update\" was called.");
        Book checkBook = bookRepository.findByIsbn(bookDto.getIsbn());
        if (checkBook != null && checkBook.getId() != bookDto.getId()) {
            logger.error("Book with such ISBN already exists: " + checkBook.getIsbn());
            throw new RuntimeException("Book with such ISBN already exists: " + checkBook.getIsbn());
        }
        Book book = dtoToBook(bookDto);

        book = bookRepository.save(book);
        return bookToDto(book);
    }

    @Override
    @Transactional
    public BookDto save(BookDto bookDto) {
        logger.debug("Service method \"save\" was called.");
        Book checkBook = bookRepository.findByIsbn(bookDto.getIsbn());
        if (checkBook != null && checkBook.getId() != bookDto.getId()) {
            logger.error("Book with such ISBN already exists: " + checkBook.getIsbn());
            throw new RuntimeException("Book with such ISBN already exists: " + checkBook.getIsbn());
        }
        Book book = dtoToBook(bookDto);
        book = bookRepository.save(book);
        return bookToDto(book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logger.debug("Service method \"delete\" was called.");
        bookRepository.delete(id);
    }

    @Override
    public Long countAll() {
        logger.debug("Service method \"countAll\" was called.");
        return bookRepository.count();
    }

}

