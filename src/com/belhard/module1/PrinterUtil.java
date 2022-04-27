package com.belhard.module1;

public class PrinterUtil {

    public static void showBriefInfo(Book book){
        System.out.println("Id = " + book.getId() + ", title = " + book.getTitle() + ", author = " + book.getAuthor());
    }
}
