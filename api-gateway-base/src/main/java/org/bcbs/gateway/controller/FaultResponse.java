package org.bcbs.gateway.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;

class FaultResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final Date timestamp;
    private final int status;
    private final String message;

    FaultResponse(HttpStatus status, Throwable cause) {
        this.timestamp = new Date();
        this.status = status.value();
        this.message = (cause != null && cause.getMessage() != null && !cause.getMessage().isEmpty()) ?
                cause.getMessage() : status.getReasonPhrase();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
