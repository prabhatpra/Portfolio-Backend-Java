package com.prabhat.portfolio.exception;

public class InvalidCredentialException extends ApplicationException {

    public InvalidCredentialException() {
        super(ErrorCodeEnum.INVALID_CREDENTIALS);
    }
}