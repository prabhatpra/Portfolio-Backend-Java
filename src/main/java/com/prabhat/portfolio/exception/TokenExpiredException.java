package com.prabhat.portfolio.exception;

public class TokenExpiredException extends ApplicationException {

    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}