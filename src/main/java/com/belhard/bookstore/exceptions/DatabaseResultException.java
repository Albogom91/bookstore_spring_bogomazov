package com.belhard.bookstore.exceptions;

import javax.persistence.NoResultException;

public class DatabaseResultException extends NoResultException {

    public DatabaseResultException(String message) {
        super(message);
    }
}
