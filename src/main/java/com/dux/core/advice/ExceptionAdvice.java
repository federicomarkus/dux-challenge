package com.dux.core.advice;

import com.dux.core.exceptions.ApiException;
import com.dux.core.response.ResponseError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleError(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseError.builder()
                .mensaje("Hubo un problema en el servidor.")
                .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity handleApiError(ApiException exception) {
        return ResponseEntity.status(exception.getEstado()).body(ResponseError.builder()
                .mensaje(exception.getMensaje())
                .codigo(exception.getEstado().value()).build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationError(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseError.builder()
                .mensaje("La solicitud es invalida")
                .codigo(HttpStatus.BAD_REQUEST.value()).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidError(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseError.builder()
                .mensaje("La solicitud es invalida")
                .codigo(HttpStatus.BAD_REQUEST.value()).build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseError.builder()
                .mensaje("Hubo un problema en la base de datos.")
                .codigo(HttpStatus.BAD_REQUEST.value()).build());
    }

}
