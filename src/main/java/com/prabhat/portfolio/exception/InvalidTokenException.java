package com.prabhat.portfolio.exception;

public class InvalidTokenException extends ApplicationException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}