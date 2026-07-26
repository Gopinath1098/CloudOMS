package com.cloud.oms.app.exception;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Map<String, String>> ProductNotValid(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // New handler for validation errors
    @ExceptionHandler({ConstraintViolationException.class, TransactionSystemException.class})
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(v -> {
            errors.put(v.getPropertyPath().toString(), v.getMessage());
        });
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({OrderNotFoundException.class, OrderNotCreatedException.class, OutofStockException.class,Exception.class})
    public ResponseEntity<Map<String, String>> handleOrderNotFound(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        ex.printStackTrace();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
}
