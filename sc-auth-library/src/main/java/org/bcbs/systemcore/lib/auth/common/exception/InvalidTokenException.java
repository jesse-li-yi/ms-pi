package org.bcbs.systemcore.lib.auth.common.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException() {
        super("Token is invalid or expired.");
    }
}
