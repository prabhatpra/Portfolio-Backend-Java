package com.prabhat.portfolio.exception;

public class RateLimitException extends ApplicationException {

    public RateLimitException() {
        super(ErrorCodeEnum.RATE_LIMIT_EXCEEDED);
    }
}