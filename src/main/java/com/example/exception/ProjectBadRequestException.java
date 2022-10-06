package com.example.exception;


import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class ProjectBadRequestException extends RuntimeException {
    public ProjectBadRequestException(String message) {
        super(message);
    }
}

