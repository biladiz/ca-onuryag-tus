package com.tus.java.oop1.rms.data.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ControllerAdvice
public class RSMExceptionHandler {


    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex, Locale locale) {
        String message = messageSource.getMessage("error.recordExists", null, locale);
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, Locale locale) {
        String message = messageSource.getMessage("error.malformedJson", null, locale);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}

