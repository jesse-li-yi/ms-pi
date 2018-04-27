package org.bcbs.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
@Order(2)
@ConditionalOnProperty(prefix = "api-gateway.transmission", value = "cache")
class CacheFilter extends OncePerRequestFilter {

    // Cache ttl value in seconds.
    private static final long REQUEST_CACHE_TTL = 120L;

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public CacheFilter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (HttpMethod.GET.matches(httpServletRequest.getMethod())) {
            String key = httpServletRequest.getRequestURI();
            String data = this.redisTemplate.opsForValue().get(key);

            if (data != null && !data.isEmpty()) {
                PrintWriter writer = httpServletResponse.getWriter();
                writer.write(data);
                writer.flush();
            } else {
                CachedResponse cachedResponse = new CachedResponse(httpServletResponse);

                filterChain.doFilter(httpServletRequest, cachedResponse);
                cachedResponse.flushBuffer();

                if (cachedResponse.getStatus() == HttpServletResponse.SC_OK)
                    this.redisTemplate.opsForValue().set(key, cachedResponse.getContent(),
                            CacheFilter.REQUEST_CACHE_TTL, TimeUnit.SECONDS);
            }
        } else
            filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    // Response wrapper for data caching.
    private static class CachedResponse extends HttpServletResponseWrapper {

        private final ByteArrayOutputStream baos;

        CachedResponse(HttpServletResponse response) {
            super(response);
            this.baos = new ByteArrayOutputStream();
        }

        @Override
        public PrintWriter getWriter() {
            return new PrintWriter(getOutputStream());
        }

        @Override
        public ServletOutputStream getOutputStream() {
            return new ServletOutputStream() {
                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                    // do nothing
                }

                @Override
                public void write(int b) {
                    CachedResponse.this.baos.write(b);
                }
            };
        }

        @Override
        public void flushBuffer() throws IOException {
            if (!isCommitted()) {
                resetBuffer();

                PrintWriter writer = super.getWriter();
                writer.write(getContent());
                writer.flush();
            }
        }

        String getContent() {
            return this.baos.toString();
        }
    }
}
