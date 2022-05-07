package com.belhard.bookstore.util;

import com.belhard.bookstore.service.dto.BookDto;

public class PrinterUtil {

    public static void showBriefInfo(BookDto bookDto) {
        System.out.println("Id = " + bookDto.getId() + ", title = " + bookDto.getTitle() + ", author = " + bookDto.getAuthor());
    }
}
