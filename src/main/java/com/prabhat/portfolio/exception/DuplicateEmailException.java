package com.prabhat.portfolio.exception;

public class DuplicateEmailException extends ApplicationException {

    public DuplicateEmailException() {
        super(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}