package com.Golosov.services.exceptions;

/**
 * Created by Андрей on 17.05.2017.
 */
public class ServiceException extends RuntimeException{

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }
}
