package org.bcbs.systemcore.lib.auth.provider.authentication;

import org.bcbs.systemcore.lib.auth.provider.ClientDetailsService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class ClientPreAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BASIC_TYPE_PRIFIX = "Basic ";

    private ClientDetailsService clientDetailsService;

    public ClientPreAuthenticationFilter(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Make sure Basic authentication only allow client authentication.
        String header = request.getHeader(AUTH_HEADER);
        if (header != null && header.startsWith(BASIC_TYPE_PRIFIX)) {
            String[] values = extractAndDecodeHeader(header);
            if (values.length != 2)
                throw new BadCredentialsException("Invalid basic authentication token");

            this.clientDetailsService.loadClientByClientId(values[0]);
        }
        filterChain.doFilter(request, response);
    }

    private String[] extractAndDecodeHeader(String header) throws IOException {
        byte[] base64Token = header.substring(BASIC_TYPE_PRIFIX.length()).getBytes("UTF-8");
        byte[] decoded;

        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException ex) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
        return new String(decoded, "UTF-8").split(":");
    }
}
