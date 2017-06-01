package com.golosov.services.exceptions;

/**
 * Created by Андрей on 31.05.2017.
 */
public class ExistUserException extends RuntimeException {

    public ExistUserException() {
        super();
    }

    public ExistUserException(String message) {
        super(message);
    }
}
