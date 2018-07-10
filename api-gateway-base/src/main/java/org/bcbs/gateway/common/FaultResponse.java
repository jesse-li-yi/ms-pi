package org.bcbs.gateway.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.Date;

public class FaultResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final Date timestamp;
    private final int status;
    private final String message;

    public FaultResponse(HttpStatus status, Throwable cause) {
        this.timestamp = new Date();
        this.status = status.value();
        this.message = (cause != null && !StringUtils.isEmpty(cause.getMessage())) ?
                cause.getMessage() : status.getReasonPhrase();
    }

    // Getter only.
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
