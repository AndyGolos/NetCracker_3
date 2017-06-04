package com.golosov.services.exceptions;

/**
 * Created by Андрей on 04.06.2017.
 */
public class CardStatusException extends RuntimeException {

    public CardStatusException() {
    }

    public CardStatusException(String message) {
        super(message);
    }
}
