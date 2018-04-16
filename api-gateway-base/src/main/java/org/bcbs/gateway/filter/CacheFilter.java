package org.bcbs.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
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
class CacheFilter implements Filter {

    // Cache ttl value in seconds.
    private static final long CACHE_TTL = 300L;

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public CacheFilter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if (httpRequest.getMethod().equalsIgnoreCase("GET")) {
            String cacheKey = httpRequest.getRequestURI();
            if (this.redisTemplate.hasKey(cacheKey)) {
                PrintWriter writer = httpResponse.getWriter();
                String data = this.redisTemplate.opsForValue().get(cacheKey);
                writer.write(data);
                writer.flush();
            } else {
                CachedResponse cachedResponse = new CachedResponse(httpResponse);

                filterChain.doFilter(httpRequest, cachedResponse);

                cachedResponse.flushBuffer();

                if (cachedResponse.getStatus() == HttpServletResponse.SC_OK)
                    this.redisTemplate.opsForValue().set(cacheKey, cachedResponse.getContent(),
                            CacheFilter.CACHE_TTL, TimeUnit.SECONDS);
            }
        } else
            filterChain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {
        // do nothing
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
