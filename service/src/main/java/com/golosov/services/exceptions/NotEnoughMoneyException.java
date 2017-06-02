package com.golosov.services.exceptions;

/**
 * Created by Андрей on 02.06.2017.
 */
public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException() {
        super();
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
