package org.bcbs.systemcore.lib.auth.common.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenGrantException extends AuthenticationException {

    public TokenGrantException() {
        super("Failed to grant access token.");
    }
}
