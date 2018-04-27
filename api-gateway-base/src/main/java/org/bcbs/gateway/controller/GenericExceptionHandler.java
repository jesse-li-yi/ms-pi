package org.bcbs.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
class GenericExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public FaultResponse genericExceptionHandler(Exception ex) {
        return new FaultResponse(HttpStatus.SERVICE_UNAVAILABLE, ex);
    }
}
