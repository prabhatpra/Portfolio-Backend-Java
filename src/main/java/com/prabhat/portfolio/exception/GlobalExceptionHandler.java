package com.prabhat.portfolio.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

    @RestControllerAdvice
    @Slf4j
    public class GlobalExceptionHandler {

        @ExceptionHandler(ApplicationException.class)
        public ResponseEntity<ErrorRes> handleApplicationException(ApplicationException ex) {

            log.error("ApplicationException occurred: {}", ex.getMessage());

            ErrorRes response = ErrorRes.builder()
                    .errorCode(ex.getErrorCode())
                    .message(ex.getMessage())
                    .status(ex.getHttpStatus().value())
                    .timestamp(LocalDateTime.now())
                    .build();

            return ResponseEntity
                    .status(ex.getHttpStatus())
                    .body(response);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorRes> handleGenericException(Exception ex) {

            log.error("Unexpected exception occurred", ex);

            ErrorRes response = ErrorRes.builder()
                    .errorCode(ErrorCodeEnum.GENERIC_ERROR.getErrorCode())
                    .message(ErrorCodeEnum.GENERIC_ERROR.getErrorMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .timestamp(LocalDateTime.now())
                    .build();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
        
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorRes> handleValidationException(MethodArgumentNotValidException ex) {

            String message = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + " : " + error.getDefaultMessage())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Validation error");

            log.error("Validation error: {}", message);

            ErrorRes response = ErrorRes.builder()
                    .errorCode("VALIDATION_ERROR")
                    .message(message)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(LocalDateTime.now())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    
}