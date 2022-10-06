package com.example.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjectBadRequestException.class)
    public ResponseEntity<String> handlerBadRequest(ProjectBadRequestException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<String> handlerNotFound(ProjectNotFoundException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(ProjectParseException.class)
    public ResponseEntity<String> handlerParseException(ProjectParseException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}
