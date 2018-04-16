package org.bcbs.microservice.controller;

import org.bcbs.microservice.data.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class GenericExceptionHandler {


    @ExceptionHandler(value = {Exception.class})
    public GenericResponse genericExceptionHandler(Exception ex) {
        return GenericResponse.failure(ex.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public GenericResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        return GenericResponse.failure(ex.getBindingResult().hasErrors() ?
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage() : null);
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    public GenericResponse noHandlerFoundExceptionHandler() {
        return GenericResponse.failure(HttpStatus.NOT_IMPLEMENTED.value(),
                HttpStatus.NOT_IMPLEMENTED.getReasonPhrase());
    }
}
