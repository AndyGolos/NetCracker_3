package com.golosov.controllers.exceptionhandlers;

import com.golosov.services.dto.exceptionDto.FailedResponseDto;
import com.golosov.services.exceptions.ExistUserException;
import com.golosov.services.exceptions.IncorrectPasswordException;
import com.golosov.services.exceptions.NotEnoughMoneyException;
import com.golosov.services.exceptions.NotFoundException;
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
    public ResponseEntity<FailedResponseDto> notFoundHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        FailedResponseDto failedResponseDto = new FailedResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(failedResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            IncorrectPasswordException.class,
            ExistUserException.class,
            NotEnoughMoneyException.class})
    public ResponseEntity<FailedResponseDto> runtimeHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        FailedResponseDto failedResponseDto = new FailedResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(failedResponseDto, HttpStatus.BAD_REQUEST);
    }

    //TODO?
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<FailedResponseDto> exceptionHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        FailedResponseDto failedResponseDto = new FailedResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(failedResponseDto, HttpStatus.BAD_REQUEST);
    }
}
