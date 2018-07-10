package org.bcbs.systemcore.lib.auth.provider.token.granter;

import org.bcbs.systemcore.lib.auth.common.AccessToken;
import org.bcbs.systemcore.lib.auth.common.constraint.GrantType;
import org.bcbs.systemcore.lib.auth.provider.AuthServerTokenService;
import org.bcbs.systemcore.lib.auth.provider.ClientDetails;
import org.bcbs.systemcore.lib.auth.provider.TokenAuthentication;
import org.bcbs.systemcore.lib.auth.provider.TokenRequest;
import org.bcbs.systemcore.lib.auth.provider.token.AbstractTokenGranter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class PasswordTokenGranter extends AbstractTokenGranter {

    private AuthenticationManager authenticationManager;

    public PasswordTokenGranter(AuthenticationManager authenticationManager, AuthServerTokenService tokenService) {
        super(GrantType.PASSWORD, tokenService);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected AccessToken getAccessToken(ClientDetails clientDetails, TokenRequest tokenRequest) {
        Authentication userAuthentication;
        try {
            userAuthentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword()));
        } catch (AuthenticationException ex) {
            throw new InsufficientAuthenticationException(ex.getMessage());
        }

        if (userAuthentication == null || !userAuthentication.isAuthenticated())
            throw new InsufficientAuthenticationException("Could not authenticate user!");

        return this.tokenService.createAccessToken(
                new TokenAuthentication(tokenRequest.getGrantType(), clientDetails, userAuthentication));
    }
}
