package com.example.Auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedExceptionHandler extends RuntimeException {
    public UnauthorizedExceptionHandler(String msg) {
        super(msg);
    }
}
