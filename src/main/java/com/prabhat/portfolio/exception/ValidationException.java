package com.prabhat.portfolio.exception;

public class ValidationException extends ApplicationException {

    public ValidationException() {
        super(ErrorCode.VALIDATION_ERROR);
    }
    
    public ValidationException(String message) {
    	super(message, ErrorCode.VALIDATION_ERROR);
    }
}