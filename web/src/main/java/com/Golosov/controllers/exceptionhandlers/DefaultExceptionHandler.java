package com.Golosov.controllers.exceptionhandlers;

import com.Golosov.services.exceptions.IncorrectPasswordException;
import com.Golosov.services.exceptions.NotFoundException;
import com.Golosov.services.exceptions.ServiceException;
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

    //TODO нужны ли тут все хэндлры? или хватит только exceptionHandler?
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity notFoundHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity serviceHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IncorrectPasswordException.class)
    public ResponseEntity incorrectPasswordHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    public ResponseEntity operationHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exceptionHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
