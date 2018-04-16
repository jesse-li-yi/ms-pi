package org.bcbs.gateway.route;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
class ApiExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ApiFaultResponse genericExceptionHandler(Exception ex) {
        return new ApiFaultResponse(HttpStatus.SERVICE_UNAVAILABLE, ex);
    }
}
