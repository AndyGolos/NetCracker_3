package com.Golosov.exceptions;

/**
 * Created by Андрей on 16.05.2017.
 */
public class DaoException extends RuntimeException {

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }
}
