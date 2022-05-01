package com.belhard.module1;

import com.belhard.module1.dao.BookDao;
import com.belhard.module1.dao.impl.BookDaoJdbcImpl;
import com.belhard.module1.service.dto.BookDto;
import com.belhard.module1.service.BookService;
import com.belhard.module1.service.impl.BookServiceImpl;
import com.belhard.module1.util.PrinterUtil;
import com.belhard.module1.util.ReaderUtil;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class App {
    public static final String INFO = "Please, enter \"all\" if you want to see brief information about all books in the store;\n" +
            "please, enter \"get #\" where # is the id of the book about which you want to see full information;\n" +
            "please, enter \"delete #\" where # is the id of the book which you want to delete from the store;\n" +
            "please, enter \"create\" if you want to create new book by providing necessary information;\n" +
            "please, enter \"update #\" where # is the id of the book which you want to update;\n" +
            "please, enter \"exit\" if you want to finish your work with this application.\n";
    private static final BookDao BOOK_DAO = new BookDaoJdbcImpl();
    private static final BookService BOOK_SERVICE = new BookServiceImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        chooseMainMenuOption(scanner);
        scanner.close();

        System.out.print("Current number of books in the store is: ");
        System.out.println(BOOK_SERVICE.countAll());
        System.out.print("Current summary price of all books by Leo Tolstoy in the store is: ");
        System.out.println(BOOK_SERVICE.countPriceOfAllBooksByAuthor("Leo Tolstoy"));

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
                    List<BookDto> bookDtos = BOOK_SERVICE.getAll();
                    for (BookDto bookDto : bookDtos) {
                        PrinterUtil.showBriefInfo(bookDto);
                    }
                    System.out.println(divider);
                    break;
                }
                case "get": {
                    BookDto bookDto = BOOK_SERVICE.getById(Long.parseLong(options[1]));
                    System.out.println(bookDto);
                    System.out.println(divider);
                    break;
                }
                case "delete": {
                    BOOK_SERVICE.delete(Long.parseLong(options[1]));
                    System.out.println(divider);
                    break;
                }
                case "create": {
                    BookDto bookDto = new BookDto();
                    System.out.print("Please, enter isbn of the new book: ");
                    bookDto.setIsbn(scanner.nextLine());
                    System.out.print("Please, enter title of the new book: ");
                    bookDto.setTitle(scanner.nextLine());
                    System.out.print("Please, enter author of the new book: ");
                    bookDto.setAuthor(scanner.nextLine());
                    System.out.print("Please, enter price of the new book: ");
                    bookDto.setPrice(scanner.nextBigDecimal());
                    scanner.nextLine();
                    System.out.print("Please, enter cover id (soft, hard or special) of the new book: ");
                    bookDto.setCover(BookDto.CoverDto.valueOf(scanner.nextLine().toUpperCase()));
                    BOOK_SERVICE.create(bookDto);
                    System.out.println(divider);
                    break;
                }
                case "update": {
                    BookDto bookDto = BOOK_SERVICE.getById(Long.parseLong(options[1]));
                    System.out.print("Please, enter new isbn of the book you wish to update: ");
                    bookDto.setIsbn(scanner.nextLine());
                    System.out.print("Please, enter new title of the book you wish to update: ");
                    bookDto.setTitle(scanner.nextLine());
                    System.out.print("Please, enter new author of the book you wish to update: ");
                    bookDto.setAuthor(scanner.nextLine());
                    System.out.print("Please, enter new price of the book you wish to update: ");
                    bookDto.setPrice(scanner.nextBigDecimal());
                    scanner.nextLine();
                    System.out.print("Please, enter new cover id (soft, hard or special) of the book you wish to update: ");
                    bookDto.setCover(BookDto.CoverDto.valueOf(scanner.nextLine().toUpperCase()));
                    BOOK_SERVICE.update(bookDto);
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

