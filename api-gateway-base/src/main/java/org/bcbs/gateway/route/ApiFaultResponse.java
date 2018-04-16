package org.bcbs.gateway.route;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Value
class ApiFaultResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final Date timestamp = new Date();
    private final int status;
    private final String message;

    ApiFaultResponse(HttpStatus status, Throwable cause) {
        this.status = status.value();
        this.message = (cause != null && cause.getMessage() != null && !cause.getMessage().isEmpty()) ?
                cause.getMessage() : status.getReasonPhrase();
    }
}
