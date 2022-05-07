package com.belhard.bookstore.util;

import com.belhard.bookstore.service.dto.BookDto;

import java.io.PrintWriter;

public class ControllerUtil {

    public static void readBookDto(BookDto bookDto, PrintWriter pw) {
        String opTag = "<td>";
        String closTag = "</td>";
        pw.write("<tr>" + opTag + bookDto.getId() + closTag);
        pw.write(opTag + bookDto.getIsbn() + closTag);
        pw.write(opTag + "<a href=\"book?id=" + bookDto.getId() + "\">" + bookDto.getTitle() + "</a>" + closTag);
        pw.write(opTag + bookDto.getAuthor() + closTag);
        pw.write(opTag + bookDto.getPrice() + closTag);
        pw.write(opTag + bookDto.getCoverDto().toString().toLowerCase() + closTag + "</tr>");
    }
}
