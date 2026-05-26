package com.prabhat.portfolio.constant;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

	GENERIC_ERROR("10000", "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR),
	
	RATE_LIMIT_EXCEEDED("10001", "Too many requests", HttpStatus.TOO_MANY_REQUESTS),
	DUPLICATE_MESSAGE("10002", "Duplicate message not allowed", HttpStatus.CONFLICT),
	NOT_FOUND("10003", "Data not found", HttpStatus.NOT_FOUND),
    INVALID_STATUS("10004", "Invalid status provided", HttpStatus.BAD_REQUEST);
	
	private final String errorCode;
	private final String errorMessage;
	private final HttpStatus httpStatus;
	
	private ErrorCodeEnum(String errorCode, String errorMessage, HttpStatus httpStatus) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}
}
