package main.java.com.belhard.bookstore.util;

import main.java.com.belhard.bookstore.service.dto.BookDto;

public class PrinterUtil {

    public static void showBriefInfo(BookDto bookDto) {
        System.out.println("Id = " + bookDto.getId() + ", title = " + bookDto.getTitle() + ", author = " + bookDto.getAuthor());
    }
}
