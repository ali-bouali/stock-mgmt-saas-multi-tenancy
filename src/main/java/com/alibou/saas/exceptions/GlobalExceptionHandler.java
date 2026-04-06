package com.alibou.saas.exceptions;

import com.alibou.saas.exceptions.responses.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(value = {EntityNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(
            final EntityNotFoundException ex,
            final HttpServletRequest request
    ) {

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

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(
            final BadCredentialsException ex,
            final HttpServletRequest request
    ) {

        final ErrorResponse errorResponse = ErrorResponse.builder()
                                                         .message("Login and / or password are incorrect.")
                                                         .path(request.getRequestURI())
                                                         .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(errorResponse);
    }

    private HttpStatus getHttpStatus(final BusinessException ex) {
        if (ex instanceof DuplicateResourceException) {
            return HttpStatus.CONFLICT;
        } else if (ex instanceof UnauthorizedException) {
            return HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof TenantProvisioningException) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof InvalidRequestException) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
