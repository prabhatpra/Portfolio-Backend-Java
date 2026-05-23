package com.prabhat.portfolio.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prabhat.portfolio.exception.ContactException.DuplicateMessageException;
import com.prabhat.portfolio.exception.ContactException.InvalidStatusException;
import com.prabhat.portfolio.exception.ContactException.NotFoundException;
import com.prabhat.portfolio.exception.ContactException.RateLimitException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    
    private ResponseEntity<ApiError> buildResponse(
            String message,
            HttpStatus status,
            String path
    ) {
        return new ResponseEntity<>(
                ApiError.of(message, status, path),
                status
        );
    }

    
    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<ApiError> handleRateLimit(
            RateLimitException ex,
            HttpServletRequest request) {

        log.warn("RateLimitException: {}", ex.getMessage());

        return buildResponse(
                ex.getMessage(),
                HttpStatus.TOO_MANY_REQUESTS,
                request.getRequestURI()
        );
    }


    @ExceptionHandler(DuplicateMessageException.class)
    public ResponseEntity<ApiError> handleDuplicate(
            DuplicateMessageException ex,
            HttpServletRequest request) {

        log.warn("DuplicateMessageException: {}", ex.getMessage());

        return buildResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                request.getRequestURI()
        );
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            NotFoundException ex,
            HttpServletRequest request) {

        log.error("NotFoundException: {}", ex.getMessage());

        return buildResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                request.getRequestURI()
        );
    }

    // ================= INVALID STATUS =================
    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<ApiError> handleInvalidStatus(
            InvalidStatusException ex,
            HttpServletRequest request) {

        log.warn("InvalidStatusException: {}", ex.getMessage());

        return buildResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                request.getRequestURI()
        );
    }

   
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        validationErrors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        ));

        log.warn("Validation failed: {}", validationErrors);

        return new ResponseEntity<>(
                ApiError.builder()
                        .message("Validation Failed")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.name())
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

  
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        log.warn("IllegalArgumentException: {}", ex.getMessage());

        return buildResponse(
                "Invalid request value",
                HttpStatus.BAD_REQUEST,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected error occurred: ", ex);

        return buildResponse(
                "Something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getRequestURI()
        );
    }
}