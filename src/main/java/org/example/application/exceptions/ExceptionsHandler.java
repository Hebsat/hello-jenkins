package org.example.application.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.example.application.api.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @ExceptionHandler({NoSuchEntityException.class})
    public ResponseEntity<Response<?>> handleNoSuchEntity(NoSuchEntityException e) {
        log.warn(e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder().success(false).message(e.getLocalizedMessage()).build());
    }

    @ExceptionHandler({EmptyFieldsException.class})
    public ResponseEntity<Response<?>> handleEmptyFieldsException(EmptyFieldsException e) {
        log.warn(e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder().success(false).message(e.getLocalizedMessage()).build());
    }

    @ExceptionHandler({IncorrectDataException.class})
    public ResponseEntity<Response<?>> handleIncorrectDataException(IncorrectDataException e) {
        log.warn(e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder().success(false).message(e.getLocalizedMessage()).build());
    }
}
