package com.prabhat.portfolio.exception;

public class DuplicateEmailException extends ApplicationException {

    public DuplicateEmailException() {
        super(ErrorCodeEnum.EMAIL_ALREADY_EXISTS);
    }
}