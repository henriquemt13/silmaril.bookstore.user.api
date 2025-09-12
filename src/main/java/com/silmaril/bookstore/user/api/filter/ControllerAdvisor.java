package com.silmaril.bookstore.user.api.filter;

import com.silmaril.bookstore.user.api.exceptions.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerAdvisor {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException ex, WebRequest request) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleContraintViolationException(ConstraintViolationException ex,
          WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(e -> errors.add(
              String.format("%s", e.getMessage())));
        return new ErrorResponse(String.join(",", errors));
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
          WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getFieldErrors().forEach(e -> errors.add(
              String.format("%s: %s (%s)", e.getField(), e.getRejectedValue(),
                    e.getDefaultMessage())));
        return new ErrorResponse(String.join(",", errors));
    }
}
