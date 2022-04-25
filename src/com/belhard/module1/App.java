package com.belhard.module1;

import java.util.List;

public class App {
    private static final BookDao BOOK_DAO = new BookDaoJdbcImpl();

    public static void main(String[] args) {
        Long id = 3L;

        List<Book> books = BOOK_DAO.getAllBooks();

        for(Book book : books){
            System.out.println(book);
        }

        Book book = BOOK_DAO.getBookById(id);

        System.out.println(book);

        Book book1 = new Book();
        System.out.println(book1.getId());
        book1.setIsbn("1021");
        book1.setTitle("One Flew Over the Cuckoo's Nest");
        book1.setAuthor("Ken Kesey");

        book1 = BOOK_DAO.createBook(book1);
        System.out.println(book1);

        book1.setIsbn("1047");
        book1.setTitle("War");
        book1.setAuthor("Colonel");

        book1 = BOOK_DAO.updateBook(book1);

        System.out.println(book1);

        //BOOK_DAO.deleteBook(book1.getId());
        //book = BOOK_DAO.updateBook(book);

        //System.out.println(book);

        /*BookDaoJdbcImpl BDAO = new BookDaoJdbcImpl();
        System.out.println(BDAO.countAllBooks());

        String isbn1 = "1011";

        Book book2 = BDAO.getBookByIsbn(isbn1);

        System.out.println(book2);

        String author1 = "Leo Tolstoy";

        List<Book> books1 = BDAO.getBooksByAuthor(author1);

        for(Book b : books1){
            System.out.println(b);
        }*/



    }


}
