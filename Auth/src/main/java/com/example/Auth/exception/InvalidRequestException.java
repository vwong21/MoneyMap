package com.example.Auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException (String msg) {
        super(msg);
    }
    public InvalidRequestException (String msg, Throwable cause) {
        super(msg, cause);
    }
}
