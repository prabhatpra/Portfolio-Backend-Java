package com.prabhat.portfolio.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ContactException extends RuntimeException {

	private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    public ContactException(String errorCode, 
    		String errorMessage,
    		HttpStatus httpStatus) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}