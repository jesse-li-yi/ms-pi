package org.bcbs.systemcore.lib.auth.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import java.util.Date;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class AuthResponse {

    private final Date timestamp;

    private final int status;

    private final Object data;

    private final String message;

    private <T> AuthResponse(int status, T data, String message) {
        this.timestamp = new Date();
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> AuthResponse success(@NonNull T data) {
        return new AuthResponse(HttpStatus.OK.value(), data, null);
    }

    public static AuthResponse failure(int status, String message) {
        return new AuthResponse(status, null, message);
    }

    // Getter only.
    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
