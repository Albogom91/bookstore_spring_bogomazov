package com.belhard.module1;

public class ReaderUtil {

    public static void setCoverById(int id, Book book) {
        switch (id) {
            case 1: {
                book.setCover(Book.Cover.SOFT);
                break;
            }
            case 2: {
                book.setCover(Book.Cover.HARD);
                break;
            }
            case 3: {
                book.setCover((Book.Cover.SPECIAL));
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
