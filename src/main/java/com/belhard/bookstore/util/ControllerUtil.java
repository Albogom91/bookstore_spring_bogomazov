package com.belhard.bookstore.util;

import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.UserDto;

import java.io.PrintWriter;

public class ControllerUtil {
    private static final String OP_TAG = "<td>";
    private static final String CLOS_TAG = "</td>";

    public static void readBookDto(BookDto bookDto, PrintWriter pw) {
        pw.write("<tr>" + OP_TAG + bookDto.getId() + CLOS_TAG);
        pw.write(OP_TAG + bookDto.getIsbn() + CLOS_TAG);
        pw.write(OP_TAG + "<a href=\"book?id=" + bookDto.getId() + "\" target=\"_blank\">" + bookDto.getTitle() + "</a>" + CLOS_TAG);
        pw.write(OP_TAG + bookDto.getAuthor() + CLOS_TAG);
        pw.write(OP_TAG + bookDto.getPrice() + CLOS_TAG);
        pw.write(OP_TAG + bookDto.getCoverDto().toString().toLowerCase() + CLOS_TAG + "</tr>");
    }

    public static void readUserDto(UserDto userDto, PrintWriter pw) {
        pw.write("<tr>" + OP_TAG + userDto.getId() + CLOS_TAG);
        pw.write(OP_TAG + userDto.getFirstName() + CLOS_TAG);
        pw.write(OP_TAG + "<a href=\"user?id=" + userDto.getId() + "\" target=\"_blank\">" + userDto.getLastName() + "</a>" + CLOS_TAG);
        pw.write(OP_TAG + userDto.getEmail() + CLOS_TAG);
        pw.write(OP_TAG + userDto.getPassword() + CLOS_TAG);
        pw.write(OP_TAG + userDto.getRoleDto().toString().toLowerCase() + CLOS_TAG + "</tr>");
    }
}
