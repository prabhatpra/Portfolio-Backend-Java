package com.prabhat.portfolio.exception;

public class UnauthorizedException extends ApplicationException {

    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}