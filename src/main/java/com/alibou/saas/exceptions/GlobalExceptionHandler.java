package com.alibou.saas.exceptions;

import com.alibou.saas.exceptions.responses.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> handleException(
            final BusinessException ex,
            final HttpServletRequest request
    ) {
        log.error("Entity not found", ex);

        final ErrorResponse errorResponse = ErrorResponse.builder()
                                                         .message(ex.getMessage())
                                                         .path(request.getRequestURI())
                                                         .build();

        final HttpStatus status = getHttpStatus(ex);

        return ResponseEntity.status(status)
                             .body(errorResponse);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(
            final EntityNotFoundException ex,
            final HttpServletRequest request
    ) {
        log.error("Entity not found", ex);

        final ErrorResponse errorResponse = ErrorResponse.builder()
                                                         .code("NOT_FOUND")
                                                         .message(ex.getMessage())
                                                         .path(request.getRequestURI())
                                                         .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(errorResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(
            final MethodArgumentNotValidException ex,
            final HttpServletRequest request
    ) {
        log.error("Entity not found", ex);

        final List<ErrorResponse.ValidationError> errors = new ArrayList<>();
        ex.getBindingResult()
          .getAllErrors()
          .forEach(error -> {
              final String fieldName = ((FieldError) error).getField();
              final String errorCode = error.getDefaultMessage();
              final String defaultMessage = error.getDefaultMessage(); // todo add translation later

              errors.add(ErrorResponse.ValidationError.builder()
                                                      .field(fieldName)
                                                      .code(errorCode)
                                                      .message(defaultMessage)
                                                      .build());
          });

        final ErrorResponse errorResponse = ErrorResponse.builder()
                                                         .validationErrors(errors)
                                                         .path(request.getRequestURI())
                                                         .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(errorResponse);
    }

    private HttpStatus getHttpStatus(final BusinessException ex) {
        if (ex instanceof DuplicateResourceException) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
