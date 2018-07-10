package org.bcbs.systemcore.lib.auth.provider.authentication;

import org.bcbs.systemcore.lib.auth.common.exception.InvalidTokenException;
import org.bcbs.systemcore.lib.auth.provider.ResourceServerTokenService;
import org.bcbs.systemcore.lib.auth.provider.TokenAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_TYPE_PREFIX = "Bearer ";

    private ResourceServerTokenService resourceServerTokenService;

    public TokenAuthenticationFilter(ResourceServerTokenService resourceServerTokenService) {
        Assert.notNull(resourceServerTokenService, "Resource server token service cannot be null!");
        this.resourceServerTokenService = resourceServerTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(AUTH_HEADER);
        if (header != null && header.startsWith(BEARER_TYPE_PREFIX)) {
            String accessToken = header.substring(BEARER_TYPE_PREFIX.length());
            TokenAuthentication tokenAuthentication = this.resourceServerTokenService.loadAuthentication(accessToken);
            if (tokenAuthentication == null)
                throw new InvalidTokenException();

            PreAuthenticatedAuthenticationToken authentication = tokenAuthentication.toAuthentication();
            authentication.setDetails(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
