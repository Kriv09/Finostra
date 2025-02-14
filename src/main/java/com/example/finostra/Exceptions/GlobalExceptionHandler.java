package com.example.finostra.Exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException e) {

        var statusCode = HttpStatus.NOT_FOUND; // 404
        String message = e.getMessage();
        Date timestamp = new Date();

        ApiError apiError = new ApiError(
                statusCode.value(),
                message,
                timestamp
        );

        return new ResponseEntity<ApiError>(apiError, statusCode);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException e) {

        var statusCode = HttpStatus.BAD_REQUEST; // 400
        String message = e.getMessage();
        Date timestamp = new Date();

        ApiError apiError = new ApiError(
                statusCode.value(),
                message,
                timestamp
        );

        return new ResponseEntity<ApiError>(apiError, statusCode);
    }

}
