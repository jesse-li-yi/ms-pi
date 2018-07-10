package org.bcbs.systemcore.lib.auth.provider;

import org.bcbs.systemcore.lib.auth.common.AccessToken;
import org.bcbs.systemcore.lib.auth.common.exception.InvalidTokenException;
import org.bcbs.systemcore.lib.auth.common.exception.TokenGrantException;

public interface AuthServerTokenService {

    AccessToken createAccessToken(TokenAuthentication tokenAuthentication) throws TokenGrantException;

    AccessToken refreshAccessToken(String tokenValue) throws InvalidTokenException, TokenGrantException;

    AccessToken getAccessToken(String tokenValue) throws InvalidTokenException;
}