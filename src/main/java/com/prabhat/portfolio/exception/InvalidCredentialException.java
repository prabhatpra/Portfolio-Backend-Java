package com.prabhat.portfolio.exception;

public class InvalidCredentialException extends ApplicationException {

    public InvalidCredentialException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}