package com.agylist.sprint.controller;

import com.agylist.sprint.dto.ErrorResponse;
import com.agylist.sprint.exception.NotEligibleException;
import com.agylist.sprint.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(final NotEligibleException ex) {
        log.warn("Auth error occurred - {}", ex.getMessage());
        return build(404, ex);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(final ResourceNotFoundException ex) {
        log.warn("Resource not found - {}", ex.getMessage());
        return build(404, ex);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(final Exception ex) {
        log.warn("Unexpected error occurred - {}", ex.getMessage());
        return build(500, ex);
    }

    public ResponseEntity<ErrorResponse> build(final int status, final Exception ex) {
        return ResponseEntity.status(status).body(ErrorResponse.builder().type(ex.getClass().getSimpleName())
                .message(ex.getMessage()).offsetDateTime(OffsetDateTime.now().withNano(0)).build());
    }
}
