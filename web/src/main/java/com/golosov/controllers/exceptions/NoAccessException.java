package com.golosov.controllers.exceptions;

/**
 * Created by Андрей on 04.06.2017.
 */
public class NoAccessException extends RuntimeException {

    public NoAccessException() {
    }

    public NoAccessException(String message) {
        super(message);
    }
}
