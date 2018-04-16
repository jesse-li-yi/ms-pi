package org.bcbs.gateway.route;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.lang.NonNullApi;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
class ApiFallbackProvider implements FallbackProvider {

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new FallbackResponse(cause);
    }

    @NonNullApi
    private static class FallbackResponse extends AbstractClientHttpResponse {

        private final HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        private final ApiFaultResponse body;

        FallbackResponse(Throwable cause) {
            this.body = new ApiFaultResponse(this.status, cause);
        }

        @Override
        public HttpStatus getStatusCode() {
            return this.status;
        }

        @Override
        public int getRawStatusCode() {
            return this.status.value();
        }

        @Override
        public String getStatusText() {
            return this.status.getReasonPhrase();
        }

        @Override
        public void close() {
            // do nothing
        }

        @Override
        public InputStream getBody() {
            try {
                return new ByteArrayInputStream(new ObjectMapper().writeValueAsBytes(this.body));
            } catch (JsonProcessingException ex) {
                return new ByteArrayInputStream(ex.getMessage().getBytes());
            }
        }

        @Override
        public HttpHeaders getHeaders() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return headers;
        }
    }
}