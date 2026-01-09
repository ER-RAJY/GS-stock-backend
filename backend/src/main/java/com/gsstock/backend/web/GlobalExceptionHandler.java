package com.gsstock.backend.web;

import com.gsstock.backend.exception.*;
import com.gsstock.backend.web.dto.common.ApiError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    // =========================
    // NOT FOUND
    // =========================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    // =========================
    // BUSINESS ERRORS
    // =========================
    @ExceptionHandler({
            StockInsufficientException.class,
            BusinessException.class
    })
    public ResponseEntity<ApiError> handleBusiness(RuntimeException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    // =========================
    // VALIDATION (DTO @Valid)
    // =========================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {

        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .toList();

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                details
        );
    }

    // =========================
    // CONSTRAINT (Path / Param)
    // =========================
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraint(ConstraintViolationException ex) {

        List<String> details = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + " : " + v.getMessage())
                .toList();

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Constraint violation",
                details
        );
    }

    // =========================
    // FALLBACK (unexpected)
    // =========================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOther(Exception ex) {
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error",
                List.of(ex.getMessage())
        );
    }

    // =========================
    // BUILDER
    // =========================
    private ResponseEntity<ApiError> buildError(
            HttpStatus status,
            String message,
            List<String> details
    ) {
        ApiError error = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                message,
                LocalDateTime.now(),
                details
        );
        return new ResponseEntity<>(error, status);
    }
}
