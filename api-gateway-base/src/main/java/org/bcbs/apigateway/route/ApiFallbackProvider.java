package org.bcbs.apigateway.route;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
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

    private class FallbackResponse implements ClientHttpResponse {

        private final HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        private String message = "Sorry, service is unavailable now!";

        FallbackResponse(Throwable cause) {
            if (cause != null)
                this.message = cause.toString();
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
            return new ByteArrayInputStream(this.message.getBytes());
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