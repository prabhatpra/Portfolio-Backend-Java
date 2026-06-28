package com.prabhat.portfolio.exception;

public class NotFoundException extends ApplicationException {

    public NotFoundException() {
        super(ErrorCodeEnum.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message, ErrorCodeEnum.NOT_FOUND);
    }
}