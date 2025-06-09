package com.spring.edna.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EdnaException.class)
    public ResponseEntity<Map<String, String>> handleEdnaException(EdnaException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorResponse = new HashMap<>();

        // Collect all validation errors into a single message
        String validationErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return fieldName + ": " + errorMessage;
                })
                .collect(Collectors.joining("; "));

        errorResponse.put("message", "Validation failed: " + validationErrors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> errorResponse = new HashMap<>();

        // Extract the duplicate field and value from the exception message
        String message = ex.getMostSpecificCause().getMessage();
        String field = null;
        String value = null;

        // Parse the message to extract the duplicate key details
        if (message != null && message.contains("Key (")) {
            int startField = message.indexOf("Key (") + 5;
            int endField = message.indexOf(")=", startField);
            field = message.substring(startField, endField);

            int startValue = message.indexOf(")=(", endField) + 3;
            int endValue = message.indexOf(")", startValue);
            value = message.substring(startValue, endValue);
        }

        // Build a user-friendly error message
        if (field != null && value != null) {
            errorResponse.put("message", "Duplicate value '" + value + "' found for field '" + field + "'");
        } else {
            errorResponse.put("message", "Data integrity violation occurred");
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // Generic exception handler for any other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}