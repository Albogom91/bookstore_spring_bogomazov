package com.belhard.module1;

import java.util.List;
import java.util.Scanner;

public class App {
    private static final BookDao BOOK_DAO = new BookDaoJdbcImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        chooseMainMenuOption(scanner);
        scanner.close();

        System.out.println(BOOK_DAO.countAllBooks());
    }

    public static void chooseMainMenuOption(Scanner scanner) {
        System.out.println("Please, enter \"all\" if you want to see brief information about all books in the store;\n" +
                "please, enter \"get #\" where # is the id of the book about which you want to see full information;\n" +
                "please, enter \"delete #\" where # is the id of the book which you want to delete from the store;\n" +
                "please, enter \"create\" if you want to create new book by providing necessary information;\n" +
                "please, enter \"update #\" where # is the id of the book which you want to update;\n" +
                "please, enter \"exit\" if you want to finish your work with this application.\n");
        String str = scanner.nextLine();
        String[] options = str.split(" ");
        if (options[0].equalsIgnoreCase("all")) {
            List<Book> books = BOOK_DAO.getAllBooks();
            for (Book book : books) {
                book.showBriefInfo(book);
            }
        } else if (options[0].equalsIgnoreCase("get")) {
            Book book = BOOK_DAO.getBookById(Long.parseLong(options[1]));
            System.out.println(book);
        } else if (options[0].equalsIgnoreCase("delete")) {
            BOOK_DAO.deleteBook(Long.parseLong(options[1]));
        } else if (options[0].equalsIgnoreCase("create")) {
            System.out.println("Please, enter isbn, title and author of the new book:");
            Book book = new Book();
            book.setIsbn(scanner.nextLine());
            book.setTitle(scanner.nextLine());
            book.setAuthor(scanner.nextLine());
            BOOK_DAO.createBook(book);
        } else if (options[0].equalsIgnoreCase("update")) {
            System.out.println("Please, enter new isbn, title and author of the book you wish to update:");
            Book book = BOOK_DAO.getBookById(Long.parseLong(options[1]));
            book.setIsbn(scanner.nextLine());
            book.setTitle(scanner.nextLine());
            book.setAuthor(scanner.nextLine());
            BOOK_DAO.updateBook(book);
        } else if (options[0].equalsIgnoreCase("exit")) {
            System.exit(0);
        } else {
            System.out.println("Invalid input! Please, try again!");
            chooseMainMenuOption(scanner);
        }
    }

}
