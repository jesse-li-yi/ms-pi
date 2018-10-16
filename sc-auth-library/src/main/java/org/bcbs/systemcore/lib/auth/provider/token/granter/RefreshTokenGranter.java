package org.bcbs.systemcore.lib.auth.provider.token.granter;

import org.bcbs.systemcore.lib.auth.common.AccessToken;
import org.bcbs.systemcore.lib.auth.common.constraint.GrantType;
import org.bcbs.systemcore.lib.auth.provider.AuthServerTokenService;
import org.bcbs.systemcore.lib.auth.provider.ClientDetails;
import org.bcbs.systemcore.lib.auth.provider.token.request.TokenRequest;
import org.bcbs.systemcore.lib.auth.provider.token.AbstractTokenGranter;

public class RefreshTokenGranter extends AbstractTokenGranter {

    public RefreshTokenGranter(AuthServerTokenService tokenService) {
        super(GrantType.REFRESH_TOKEN, tokenService);
    }

    @Override
    protected AccessToken getAccessToken(ClientDetails clientDetails, TokenRequest tokenRequest) {
        return this.tokenService.refreshAccessToken(tokenRequest.getRefreshToken());
    }
}
