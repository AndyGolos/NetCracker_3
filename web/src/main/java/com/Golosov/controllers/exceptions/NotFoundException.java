package com.Golosov.controllers.exceptions;

/**
 * Created by Андрей on 24.05.2017.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
