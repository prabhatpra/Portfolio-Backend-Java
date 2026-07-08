package com.prabhat.portfolio.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus httpStatus;

    // Default message from ErrorCodeEnum
    public ApplicationException(ErrorCode error) {
        super(error.getErrorMessage());
        this.errorCode = error.getErrorCode();
        this.httpStatus = error.getHttpStatus();
    }

    // Custom message (optional)
    public ApplicationException(String message, ErrorCode error) {
        super(message);
        this.errorCode = error.getErrorCode();
        this.httpStatus = error.getHttpStatus();
    }
}