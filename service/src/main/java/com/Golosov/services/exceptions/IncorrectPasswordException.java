package com.Golosov.services.exceptions;

/**
 * Created by Андрей on 29.05.2017.
 */
public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException() {
        super();
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(Throwable cause) {
        super(cause);
    }
}
