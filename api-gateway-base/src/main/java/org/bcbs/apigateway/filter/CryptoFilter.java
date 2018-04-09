package org.bcbs.apigateway.filter;

import org.bcbs.apigateway.util.CryptoHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

@Component
@Order(4)
@ConditionalOnProperty(prefix = "api-gateway.transmission", value = "crypto")
class CryptoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        DecryptedRequest decryptedRequest = new DecryptedRequest((HttpServletRequest) servletRequest);
        EncryptedResponse encryptedResponse = new EncryptedResponse((HttpServletResponse) servletResponse);

        filterChain.doFilter(decryptedRequest, encryptedResponse);

        encryptedResponse.flushBuffer();
    }

    @Override
    public void destroy() {
        // do nothing
    }

    // Request wrapper for data decrypting.
    private class DecryptedRequest extends HttpServletRequestWrapper {

        private final ByteArrayInputStream bais;

        DecryptedRequest(HttpServletRequest request) throws IOException {
            super(request);

            InputStream is = request.getInputStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int read;
            while ((read = is.read(buff)) > 0)
                os.write(buff, 0, read);

            this.bais = new ByteArrayInputStream(CryptoHelper.xorDecrypt(os.toByteArray()));
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() {
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return true;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                    // do nothing
                }

                @Override
                public int read() {
                    return DecryptedRequest.this.bais.read();
                }
            };
        }
    }

    // Response wrapper for data encrypting.
    private class EncryptedResponse extends HttpServletResponseWrapper {

        private final ByteArrayOutputStream baos;

        EncryptedResponse(HttpServletResponse response) {
            super(response);

            this.baos = new ByteArrayOutputStream();
        }

        @Override
        public PrintWriter getWriter() throws IOException {
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
                    EncryptedResponse.this.baos.write(b);
                }
            };
        }

        @Override
        public void flushBuffer() throws IOException {
            if (!super.isCommitted()) {
                super.resetBuffer();

                PrintWriter writer = super.getWriter();
                byte[] bytes = CryptoHelper.xorEncrypt(this.baos.toByteArray());

                for (byte b : bytes)
                    writer.write(bytes[b]);
                writer.flush();

                super.setContentLength(-1);
            }
        }
    }
}
