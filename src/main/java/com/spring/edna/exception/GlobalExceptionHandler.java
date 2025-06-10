package com.spring.edna.exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

        // Collect all validation errors into a single message with translated field names
        String validationErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String translatedField = FIELD_NAME_TRANSLATIONS.getOrDefault(fieldName, fieldName);
                    String errorMessage = error.getDefaultMessage();
                    return translatedField + ": " + errorMessage;
                })
                .collect(Collectors.joining("; "));

        errorResponse.put("message", "Falha na validação: " + validationErrors);
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
            errorResponse.put("message", "Valor duplicado '" + value + "' encontrado no campo '" + field + "'");
        } else {
            errorResponse.put("message", "Violação de integração de dados ocorreu");
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // Generic exception handler for any other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Um erro inexsperado ocorreu: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static final Map<String, String> FIELD_NAME_TRANSLATIONS = Map.ofEntries(
            Map.entry("name", "nome"),
            Map.entry("description", "descrição"),
            Map.entry("categoryOther", "outra categoria"),
            Map.entry("brandOther", "outra marca"),
            Map.entry("sizeOther", "outro tamanho"),
            Map.entry("priceInCents", "preço"),
            Map.entry("fabric", "tecido"),
            Map.entry("color", "cor"),
            Map.entry("category", "categoria"),
            Map.entry("size", "tamanho"),
            Map.entry("gender", "gênero"),
            Map.entry("brand", "marca"),
            Map.entry("store", "loja"),
            Map.entry("images", "imagens"),
            Map.entry("clotheOrder", "pedido"),
            Map.entry("createdAt", "data de criação"),
            Map.entry("number", "número"),
            Map.entry("street", "rua"),
            Map.entry("neighborhood", "bairro"),
            Map.entry("city", "cidade")
    );


}