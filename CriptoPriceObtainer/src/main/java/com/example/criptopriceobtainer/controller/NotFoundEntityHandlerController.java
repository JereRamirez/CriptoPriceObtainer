package com.example.criptopriceobtainer.controller;

import com.example.criptopriceobtainer.exception.NotFoundEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundEntityHandlerController {

    @ExceptionHandler(value = NotFoundEntityException.class)
    protected ResponseEntity<Object> handleConflict(
            NotFoundEntityException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
}
