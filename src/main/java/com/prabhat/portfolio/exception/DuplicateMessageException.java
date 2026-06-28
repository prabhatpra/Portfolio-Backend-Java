package com.prabhat.portfolio.exception;

public class DuplicateMessageException extends ApplicationException {

    public DuplicateMessageException() {
        super(ErrorCodeEnum.DUPLICATE_MESSAGE);
    }
}