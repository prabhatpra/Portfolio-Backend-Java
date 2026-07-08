package com.prabhat.portfolio.exception;

public class ForbiddenException extends ApplicationException {

    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN);
    }
}