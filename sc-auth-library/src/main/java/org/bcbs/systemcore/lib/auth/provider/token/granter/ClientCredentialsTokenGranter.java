package org.bcbs.systemcore.lib.auth.provider.token.granter;

import org.bcbs.systemcore.lib.auth.common.AccessToken;
import org.bcbs.systemcore.lib.auth.common.constraint.GrantType;
import org.bcbs.systemcore.lib.auth.provider.AuthServerTokenService;
import org.bcbs.systemcore.lib.auth.provider.ClientDetails;
import org.bcbs.systemcore.lib.auth.provider.TokenAuthentication;
import org.bcbs.systemcore.lib.auth.provider.token.request.TokenRequest;
import org.bcbs.systemcore.lib.auth.provider.token.AbstractTokenGranter;

public class ClientCredentialsTokenGranter extends AbstractTokenGranter {

    public ClientCredentialsTokenGranter(AuthServerTokenService tokenService) {
        super(GrantType.CLIENT_CREDENTIALS, tokenService);
    }

    @Override
    protected AccessToken getAccessToken(ClientDetails clientDetails, TokenRequest tokenRequest) {
        return this.tokenService.createAccessToken(
                new TokenAuthentication(tokenRequest.getGrantType(), clientDetails, null));
    }
}
