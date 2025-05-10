package com.example.esercizioColloquio.exception;

import com.example.esercizioColloquio.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException ex,
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                "NOT_FOUND",
                LocalDateTime.now(),
                request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<ErrorResponse> handleUserListEmptyException(
            UserListEmpty ex,
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                "LIST_EMPTY",
                LocalDateTime.now(),
                request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
