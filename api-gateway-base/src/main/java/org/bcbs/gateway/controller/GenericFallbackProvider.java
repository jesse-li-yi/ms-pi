package org.bcbs.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
class GenericFallbackProvider implements FallbackProvider {

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new FallbackResponse(cause);
    }

    private static class FallbackResponse extends AbstractClientHttpResponse {

        private final HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        private final FaultResponse body;

        FallbackResponse(Throwable cause) {
            this.body = new FaultResponse(this.status, cause);
        }

        @Override
        @NonNull
        public HttpStatus getStatusCode() {
            return this.status;
        }

        @Override
        public int getRawStatusCode() {
            return this.status.value();
        }

        @Override
        @NonNull
        public String getStatusText() {
            return this.status.getReasonPhrase();
        }

        @Override
        public void close() {
            // do nothing
        }

        @Override
        @NonNull
        public InputStream getBody() {
            try {
                return new ByteArrayInputStream(new ObjectMapper().writeValueAsBytes(this.body));
            } catch (JsonProcessingException ex) {
                return new ByteArrayInputStream(ex.getMessage().getBytes());
            }
        }

        @Override
        @NonNull
        public HttpHeaders getHeaders() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return headers;
        }
    }
}