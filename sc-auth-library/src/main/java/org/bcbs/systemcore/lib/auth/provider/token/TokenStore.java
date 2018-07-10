package org.bcbs.systemcore.lib.auth.provider.token;

import org.bcbs.systemcore.lib.auth.common.AccessToken;
import org.bcbs.systemcore.lib.auth.common.RefreshToken;
import org.bcbs.systemcore.lib.auth.provider.TokenAuthentication;

public interface TokenStore {

    boolean storeToken(AccessToken accessToken, RefreshToken refreshToken, TokenAuthentication auth);

    AccessToken readAccessToken(TokenAuthentication auth);

    AccessToken readAccessToken(String tokenValue);

    void removeAccessToken(String tokenValue);

    RefreshToken readRefreshToken(TokenAuthentication auth);

    RefreshToken readRefreshToken(String tokenValue);

    void removeRefreshToken(String tokenValue);
}
