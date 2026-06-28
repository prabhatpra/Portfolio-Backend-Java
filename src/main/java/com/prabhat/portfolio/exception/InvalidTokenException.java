package com.prabhat.portfolio.exception;

public class InvalidTokenException extends ApplicationException {

    public InvalidTokenException() {
        super(ErrorCodeEnum.INVALID_TOKEN);
    }
}