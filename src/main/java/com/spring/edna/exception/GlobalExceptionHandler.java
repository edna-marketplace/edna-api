package com.spring.edna.exception;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<String> handleEdnaException(EdnaException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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

        // Build a user-friendly error response
        if (field != null && value != null) {
            errorResponse.put("error", "Duplicate value found");
            errorResponse.put("field", field);
            errorResponse.put("value", value);
        } else {
            errorResponse.put("error", "Data integrity violation occurred");
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}

