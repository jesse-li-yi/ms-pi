package org.bcbs.gateway.filter;

import org.bcbs.gateway.util.CryptoHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

@Component
@Order(2)
@ConditionalOnProperty(prefix = "api-gateway", value = "data-crypto", havingValue = "true")
class CryptoFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        DecryptedRequest decryptedRequest = new DecryptedRequest(httpServletRequest);
        EncryptedResponse encryptedResponse = new EncryptedResponse(httpServletResponse);

        filterChain.doFilter(decryptedRequest, encryptedResponse);
        encryptedResponse.flushBuffer();
    }

    // Request wrapper for data decrypting.
    private static class DecryptedRequest extends HttpServletRequestWrapper {

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
        public BufferedReader getReader() {
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
    private static class EncryptedResponse extends HttpServletResponseWrapper {

        private final ByteArrayOutputStream baos;

        EncryptedResponse(HttpServletResponse response) {
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
