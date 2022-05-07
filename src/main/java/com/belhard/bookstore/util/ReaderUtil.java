package com.belhard.bookstore.util;

import com.belhard.bookstore.dao.beans.Book;
import com.belhard.bookstore.service.dto.BookDto;

public class ReaderUtil {

    public static void setCoverById(int id, BookDto bookDto) {
        switch (id) {
            case 1: {
                bookDto.setCoverDto(BookDto.CoverDto.SOFT);
                break;
            }
            case 2: {
                bookDto.setCoverDto(BookDto.CoverDto.HARD);
                break;
            }
            case 3: {
                bookDto.setCoverDto((BookDto.CoverDto.SPECIAL));
                break;
            }
            default: {
                System.out.println("There was a mistake while assigning cover to the book!");
                break;
            }
        }
    }

    public static int getIdByCover(Book book) {
        switch (book.getCover()) {
            case SOFT: {
                return 1;
            }
            case HARD: {
                return 2;
            }
            case SPECIAL: {
                return 3;
            }
            default: {
                System.out.println("There was a mistake while getting cover id!");
                return 0;
            }
        }
    }
}

