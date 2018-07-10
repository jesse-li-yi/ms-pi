package org.bcbs.systemcore.lib.auth.provider.token;

import org.bcbs.systemcore.lib.auth.common.AccessToken;
import org.bcbs.systemcore.lib.auth.provider.ClientDetails;
import org.bcbs.systemcore.lib.auth.provider.TokenRequest;

public interface TokenGranter {

    AccessToken grant(ClientDetails clientDetails, TokenRequest tokenRequest);
}
