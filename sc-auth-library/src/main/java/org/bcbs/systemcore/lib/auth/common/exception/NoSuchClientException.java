package org.bcbs.systemcore.lib.auth.common.exception;

import org.springframework.security.core.AuthenticationException;

public class NoSuchClientException extends AuthenticationException {

    public NoSuchClientException(String clientId) {
        super("No client found by given id: " + clientId);
    }
}
