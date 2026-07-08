package com.prabhat.portfolio.exception;

public class NotFoundException extends ApplicationException {

    public NotFoundException() {
        super(ErrorCode.CONTACT_NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message, ErrorCode.CONTACT_NOT_FOUND);
    }
}