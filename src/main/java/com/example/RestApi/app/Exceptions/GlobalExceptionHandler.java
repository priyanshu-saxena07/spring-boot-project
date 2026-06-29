package com.example.RestApi.app.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger( GlobalExceptionHandler.class);

    @ExceptionHandler({UserNotFoundException.class,IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException9(
            Exception exception
    ) {
        logger.error("Error when finding user: ",exception);
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("Status",  HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error",  "Bad request");
        errorResponse.put("message", exception.getMessage());


        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
