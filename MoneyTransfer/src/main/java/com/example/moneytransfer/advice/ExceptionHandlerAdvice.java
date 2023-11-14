package com.example.moneytransfer.advice;

import com.example.moneytransfer.advice.model.BadResponse;
import com.example.moneytransfer.exceptions.ConfirmationFail;
import com.example.moneytransfer.exceptions.InvalidDataException;
import com.example.moneytransfer.exceptions.ServerError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<BadResponse> ideHandler(InvalidDataException exception) {
        logger.error("InvalidDataException id " + exception.getId() + " " +  exception.getMessage());
        return new ResponseEntity<>(new BadResponse(exception.getMessage(), exception.getId()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConfirmationFail.class)
    public ResponseEntity<BadResponse> cfHandler(ConfirmationFail exception) {
        logger.error("ConfirmationFail id " + exception.getId() + " " +  exception.getMessage());
        return new ResponseEntity<>(new BadResponse(exception.getMessage(), exception.getId()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerError.class)
    public ResponseEntity<BadResponse> ceHandler(ServerError exception) {
        logger.error("ServerError id " + exception.getId() + " " +  exception.getMessage());
        return new ResponseEntity<>(new BadResponse(exception.getMessage(), exception.getId()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
