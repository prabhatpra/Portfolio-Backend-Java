package com.prabhat.portfolio.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex) {

        log.error("ApplicationException occurred: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
        		.success(false)
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .status(ex.getHttpStatus().value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .reduce((a, b) -> a + ", " + b)
                .orElse(ErrorCode.VALIDATION_ERROR.getErrorMessage());

        log.error("Validation error: {}", message);

        ErrorResponse response = ErrorResponse.builder()
        		.success(false)
                .errorCode(ErrorCode.VALIDATION_ERROR.getErrorCode())
                .message(message)
                .status(ErrorCode.VALIDATION_ERROR.getHttpStatus().value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(ErrorCode.VALIDATION_ERROR.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        log.error("Unexpected exception occurred", ex);

        ErrorResponse response = ErrorResponse.builder()
        		.success(false)
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode())
                .message(ErrorCode.INTERNAL_SERVER_ERROR.getErrorMessage())
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus().value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(response);
    }
}