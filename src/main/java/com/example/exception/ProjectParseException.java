package com.example.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public class ProjectParseException extends RuntimeException{
    public ProjectParseException(String message) {
        super(message);
    }
}
