package com.belhard.module1;

import java.util.List;
import java.util.Scanner;

public class App {
    public static final String INFO = "Please, enter \"all\" if you want to see brief information about all books in the store;\n" +
            "please, enter \"get #\" where # is the id of the book about which you want to see full information;\n" +
            "please, enter \"delete #\" where # is the id of the book which you want to delete from the store;\n" +
            "please, enter \"create\" if you want to create new book by providing necessary information;\n" +
            "please, enter \"update #\" where # is the id of the book which you want to update;\n" +
            "please, enter \"exit\" if you want to finish your work with this application.\n";
    private static final BookDao BOOK_DAO = new BookDaoJdbcImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        chooseMainMenuOption(scanner);
        scanner.close();
        System.out.print("Current number of books in the store is: ");
        System.out.println(BOOK_DAO.countAllBooks());

    }

    public static void chooseMainMenuOption(Scanner scanner) {
        while (true) {
            String divider = "-----------------------------------";
            System.out.print(INFO);
            System.out.println(divider);
            String input = scanner.nextLine();
            String[] options = input.split(" ");
            switch (options[0]) {
                case "all": {
                    List<Book> books = BOOK_DAO.getAllBooks();
                    for (Book book : books) {
                        PrinterUtil.showBriefInfo(book);
                    }
                    System.out.println(divider);
                    break;
                }
                case "get": {
                    Book book = BOOK_DAO.getBookById(Long.parseLong(options[1]));
                    System.out.println(book);
                    System.out.println(divider);
                    break;
                }
                case "delete": {
                    BOOK_DAO.deleteBook(Long.parseLong(options[1]));
                    System.out.println(divider);
                    break;
                }
                case "create": {
                    Book book = new Book();
                    System.out.print("Please, enter isbn of the new book: ");
                    book.setIsbn(scanner.nextLine());
                    System.out.print("Please, enter title of the new book: ");
                    book.setTitle(scanner.nextLine());
                    System.out.print("Please, enter author of the new book: ");
                    book.setAuthor(scanner.nextLine());
                    System.out.print("Please, enter price of the new book: ");
                    book.setPrice(scanner.nextBigDecimal());
                    System.out.print("Please, enter cover id (1 for soft, 2 or hard, 3 for special) of the new book: ");
                    ReaderUtil.setCoverById(scanner.nextInt(), book);
                    BOOK_DAO.createBook(book);
                    scanner.nextLine();
                    System.out.println(divider);
                    break;
                }
                case "update": {
                    Book book = BOOK_DAO.getBookById(Long.parseLong(options[1]));
                    System.out.print("Please, enter new isbn of the book you wish to update: ");
                    book.setIsbn(scanner.nextLine());
                    System.out.print("Please, enter new title of the book you wish to update: ");
                    book.setTitle(scanner.nextLine());
                    System.out.print("Please, enter new author of the book you wish to update: ");
                    book.setAuthor(scanner.nextLine());
                    System.out.print("Please, enter new price of the book you wish to update: ");
                    book.setPrice(scanner.nextBigDecimal());
                    System.out.print("Please, enter new cover id (1 for soft, 2 or hard, 3 for special) of the book you wish to update: ");
                    ReaderUtil.setCoverById(scanner.nextInt(), book);
                    BOOK_DAO.updateBook(book);
                    scanner.nextLine();
                    System.out.println(divider);
                    break;
                }
                case "exit": {
                    return;
                }
                default: {
                    System.out.println("Invalid input! Please, try again!");
                    System.out.println(divider);
                    break;
                }
            }
        }
    }
}
