package org.bcbs.systemcore.lib.auth.common.exception;

import org.springframework.security.core.AuthenticationException;

public class SignupException extends AuthenticationException {

    public SignupException(String message) {
        super(message);
    }
}
