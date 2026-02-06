package com.example.Auth.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomerExceptionHandler {
    
    @ExceptionHandler(value = ExistingUserException.class)
    public ResponseEntity<?> handleExistingUserException(ExistingUserException exception, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("TimeStamp", new Date());
        body.put("Message", exception.getMessage());
        body.put("Path", request.getDescription(false)); 

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = InvalidRequestException.class)
    public ResponseEntity<?> handleInvalidRequestException(InvalidRequestException exception, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("TimeStamp", new Date());
        body.put("Message", exception.getMessage());
        body.put("Path", request.getDescription(false)); 

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException exception, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("TimeStamp", new Date());
        body.put("Message", exception.getMessage());
        body.put("Path", request.getDescription(false)); 

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
}
