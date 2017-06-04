package com.golosov.controllers.exceptionhandlers;

import com.golosov.controllers.exceptions.NoAccessException;
import com.golosov.controllers.responses.FailedResponse;
import com.golosov.services.exceptions.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Андрей on 25.05.2017.
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    private final Logger logger = Logger.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<FailedResponse> notFoundHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        FailedResponse failedResponse = new FailedResponse(e.getMessage(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(failedResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoAccessException.class)
    public ResponseEntity<FailedResponse> noAccessHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        FailedResponse failedResponse = new FailedResponse(e.getMessage(), HttpStatus.FORBIDDEN.toString());
        return new ResponseEntity<>(failedResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {
            IncorrectPasswordException.class,
            ExistUserException.class,
            NotEnoughMoneyException.class,
            CardStatusException.class
    })
    public ResponseEntity<FailedResponse> runtimeHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        FailedResponse failedResponse = new FailedResponse(e.getMessage(), HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(failedResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<FailedResponse> exceptionHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        FailedResponse failedResponse = new FailedResponse(e.getMessage(), HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(failedResponse, HttpStatus.BAD_REQUEST);
    }
}
