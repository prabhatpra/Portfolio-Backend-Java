package com.prabhat.portfolio.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

	 // Contact
    DUPLICATE_MESSAGE("CNT_001", "Duplicate message found", HttpStatus.CONFLICT),
    NOT_FOUND("CNT_002", "Contact not found", HttpStatus.NOT_FOUND),
    RATE_LIMIT_EXCEEDED("CNT_003", "Too many requests", HttpStatus.TOO_MANY_REQUESTS),
    VALIDATION_ERROR("GEN_002", "Validation failed", HttpStatus.BAD_REQUEST),

    // Auth
    EMAIL_ALREADY_EXISTS("AUTH_001", "Email already exists", HttpStatus.CONFLICT),
    PASSWORD_MISMATCH("AUTH_002", "Password and confirm password do not match", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS("AUTH_003", "Invalid email or password", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("AUTH_004", "Invalid JWT token", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("AUTH_005", "JWT token has expired", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("AUTH_006", "Unauthorized access", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("AUTH_007", "Access denied", HttpStatus.FORBIDDEN),

    // Generic
    GENERIC_ERROR("GEN_001", "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;
    
}

