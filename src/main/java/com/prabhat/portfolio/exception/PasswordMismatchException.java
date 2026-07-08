package com.prabhat.portfolio.exception;

public class PasswordMismatchException extends ApplicationException {

    public PasswordMismatchException() {
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}