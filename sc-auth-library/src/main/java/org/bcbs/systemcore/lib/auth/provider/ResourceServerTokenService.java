package org.bcbs.systemcore.lib.auth.provider;

import org.bcbs.systemcore.lib.auth.common.exception.InvalidTokenException;

public interface ResourceServerTokenService {

    TokenAuthentication loadAuthentication(String tokenValue) throws InvalidTokenException;
}