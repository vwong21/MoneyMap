package com.example.Auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistingUserException extends RuntimeException {
    public ExistingUserException(String msg) {
        super(msg);
    }

    public ExistingUserException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
