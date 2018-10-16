package org.bcbs.systemcore.lib.auth.provider.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        new ObjectMapper().writeValue(response.getWriter(),
                new AuthenticationFailureResponse(HttpServletResponse.SC_FORBIDDEN, authException.getMessage()));
        response.flushBuffer();
    }

    private class AuthenticationFailureResponse {

        private final Date timestamp;
        private final int status;
        private final String message;

        AuthenticationFailureResponse(int status, String message) {
            this.timestamp = new Date();
            this.status = status;
            this.message = message;
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
}
