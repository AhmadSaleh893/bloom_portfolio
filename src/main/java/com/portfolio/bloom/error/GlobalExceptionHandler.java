package com.portfolio.bloom.error;

import com.mongodb.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler for REST API errors.
 * Provides consistent error response structure across the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<List<ErrorResponse>> handleCommonException(CommonException ex) {
        List<ErrorResponse> errorResponses = new ArrayList<>();

        if (ex.getArgs() != null && ex.getArgs().length > 0) {
            String formattedTitle = String.format(ex.getErrorEnum().getTitle(), (ex.getArgs()[0] + " "));
            errorResponses.add(new ErrorResponse(
                    ex.getErrorEnum().getCode(), 
                    formattedTitle, 
                    ex.getErrorEnum().getMessageId()));
        } else {
            errorResponses.add(new ErrorResponse(
                    ex.getErrorEnum().getCode(), 
                    ex.getErrorEnum().getTitle(), 
                    ex.getErrorEnum().getMessageId()));
        }

        return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        
        // Extract field-specific error messages
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorResponses.add(new ErrorResponse(error.getField(), error.getDefaultMessage()));
        }

        return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<List<ErrorResponse>> handleUsernameNotFoundException(
            UsernameNotFoundException ex) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        errorResponses.add(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), 
                HttpStatus.NOT_FOUND.name(), 
                "User not found"));

        return new ResponseEntity<>(errorResponses, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<List<ErrorResponse>> handleBadCredentialsException(
            BadCredentialsException ex) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        errorResponses.add(new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(), 
                HttpStatus.UNAUTHORIZED.name(), 
                "Username or Password is incorrect"));

        return new ResponseEntity<>(errorResponses, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<List<ErrorResponse>> handleAccessDeniedException(
            AccessDeniedException ex) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        errorResponses.add(new ErrorResponse(
                HttpStatus.FORBIDDEN.value(), 
                HttpStatus.FORBIDDEN.name(), 
                "Access denied. Insufficient permissions."));
        
        return new ResponseEntity<>(errorResponses, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<List<ErrorResponse>> handleDuplicateKeyException(
            DuplicateKeyException ex) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        errorResponses.add(new ErrorResponse(
                HttpStatus.CONFLICT.value(), 
                HttpStatus.CONFLICT.name(), 
                "Duplicate key violation. Record already exists."));

        return new ResponseEntity<>(errorResponses, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<List<ErrorResponse>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        errorResponses.add(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), 
                HttpStatus.BAD_REQUEST.name(), 
                ex.getMessage()));

        return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ErrorResponse>> handleGenericException(Exception ex) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        errorResponses.add(new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                HttpStatus.INTERNAL_SERVER_ERROR.name(), 
                "An unexpected error occurred"));

        // Log the exception in production (logger should be used here)
        ex.printStackTrace();

        return new ResponseEntity<>(errorResponses, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
