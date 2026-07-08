package com.prabhat.portfolio.exception;

public class RateLimitException extends ApplicationException {

    public RateLimitException() {
        super(ErrorCode.RATE_LIMIT_EXCEEDED);
    }
}