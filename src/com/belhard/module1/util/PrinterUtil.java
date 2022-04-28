package com.belhard.module1.util;

import com.belhard.module1.service.dto.BookDto;

public class PrinterUtil {

    public static void showBriefInfo(BookDto bookDto) {
        System.out.println("Id = " + bookDto.getId() + ", title = " + bookDto.getTitle() + ", author = " + bookDto.getAuthor());
    }
}
