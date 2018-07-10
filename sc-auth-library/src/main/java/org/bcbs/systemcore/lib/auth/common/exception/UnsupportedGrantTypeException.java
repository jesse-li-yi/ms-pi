package org.bcbs.systemcore.lib.auth.common.exception;

import org.springframework.security.core.AuthenticationException;

public class UnsupportedGrantTypeException extends AuthenticationException {

    public UnsupportedGrantTypeException(String grantType) {
        super("Unsupported grant type: " + grantType);
    }
}
