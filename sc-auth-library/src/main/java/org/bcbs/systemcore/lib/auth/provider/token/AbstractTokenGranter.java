package org.bcbs.systemcore.lib.auth.provider.token;

import org.bcbs.systemcore.lib.auth.common.AccessToken;
import org.bcbs.systemcore.lib.auth.common.constraint.GrantType;
import org.bcbs.systemcore.lib.auth.provider.AuthServerTokenService;
import org.bcbs.systemcore.lib.auth.provider.ClientDetails;
import org.bcbs.systemcore.lib.auth.provider.token.request.TokenRequest;

public abstract class AbstractTokenGranter implements TokenGranter {

    protected final GrantType grantType;
    protected final AuthServerTokenService tokenService;

    protected AbstractTokenGranter(GrantType grantType, AuthServerTokenService tokenService) {
        this.tokenService = tokenService;
        this.grantType = grantType;
    }

    @Override
    public AccessToken grant(ClientDetails clientDetails, TokenRequest tokenRequest) {
        return !this.grantType.equals(tokenRequest.getGrantType()) ? null :
                getAccessToken(clientDetails, tokenRequest);
    }

    protected abstract AccessToken getAccessToken(ClientDetails clientDetails, TokenRequest tokenRequest);
}
