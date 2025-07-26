package com.example.matchapi.exceptions.handlers;

import com.example.matchapi.exceptions.BadRequestException;
import com.example.matchapi.exceptions.ConflictException;
import com.example.matchapi.exceptions.ObjectNotFoundException;
import com.example.matchapi.exceptions.responces.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public final ResponseEntity<?> handleNotFoundExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<?> handleConflictExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false), HttpStatus.CONFLICT);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<?> handleBadRequestExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
