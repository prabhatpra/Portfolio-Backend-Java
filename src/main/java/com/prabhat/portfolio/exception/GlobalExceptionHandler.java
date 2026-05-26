package com.prabhat.portfolio.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prabhat.portfolio.constant.ErrorCodeEnum;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ContactException.class)
    public ResponseEntity<ErrorRes> handleContactException(ContactException ex) {

        log.error("ContactException occurred: {}", ex.getErrorMessage());

        ErrorRes response = ErrorRes.builder()
                .errorCode(ex.getErrorCode())
                .message(ex.getErrorMessage())
                .status(ex.getHttpStatus().value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRes> handleGeneric(Exception ex) {

        log.error("Unexpected exception occurred: ", ex);

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
}