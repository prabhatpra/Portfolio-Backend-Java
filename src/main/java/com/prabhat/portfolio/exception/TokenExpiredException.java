package com.prabhat.portfolio.exception;

public class TokenExpiredException extends ApplicationException {

    public TokenExpiredException() {
        super(ErrorCodeEnum.TOKEN_EXPIRED);
    }
}