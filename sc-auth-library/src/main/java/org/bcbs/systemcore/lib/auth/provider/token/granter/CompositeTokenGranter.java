package org.bcbs.systemcore.lib.auth.provider.token.granter;

import org.bcbs.systemcore.lib.auth.common.AccessToken;
import org.bcbs.systemcore.lib.auth.provider.ClientDetails;
import org.bcbs.systemcore.lib.auth.provider.token.request.TokenRequest;
import org.bcbs.systemcore.lib.auth.provider.token.TokenGranter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CompositeTokenGranter implements TokenGranter {

    private final Set<TokenGranter> tokenGranters;

    public CompositeTokenGranter(Collection<TokenGranter> tokenGranters) {
        this.tokenGranters = new HashSet<>(tokenGranters);
    }

    @Override
    public AccessToken grant(ClientDetails clientDetails, TokenRequest tokenRequest) {
        AccessToken accessToken = null;
        for (TokenGranter granter : tokenGranters)
            if ((accessToken = granter.grant(clientDetails, tokenRequest)) != null)
                break;
        return accessToken;
    }
}
