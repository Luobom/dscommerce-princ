package com.w3lsolucoes.dscommerceprinc.controllers.handlers;

import com.w3lsolucoes.dscommerceprinc.dto.CustomError;
import com.w3lsolucoes.dscommerceprinc.dto.ValidationError;
import com.w3lsolucoes.dscommerceprinc.services.exceptions.DataBaseException;
import com.w3lsolucoes.dscommerceprinc.services.exceptions.ForbiddenException;
import com.w3lsolucoes.dscommerceprinc.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

//@ControllerAdvice
@RestControllerAdvice
public class CustomExceptionHandler {

    // 404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    // 400 Bad Request
    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<CustomError> dataBase(DataBaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    // 422 Unprocessable Entity
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError error = new ValidationError(Instant.now(), status.value(), "Validation exception", request.getRequestURI());
        e.getBindingResult().getFieldErrors().forEach(f -> error.addError(f.getField(), f.getDefaultMessage()));
        return ResponseEntity.status(status).body(error);
    }

    // 403 Forbidden
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomError> handleAccessDenied(AccessDeniedException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN; // 403 Forbidden
        CustomError error = new CustomError(
                Instant.now(),
                status.value(),
                "Access to the resource is prohibited to this user",
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomError> handleForbidden(ForbiddenException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN; // 403 Forbidden
        CustomError error = new CustomError(
                Instant.now(),
                status.value(),
                "Access to the resource is prohibited to this user",
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }


}