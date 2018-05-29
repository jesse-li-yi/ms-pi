package org.bcbs.microservice.oauth.server;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

@Component
class AuthClientDetailsService implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String id) throws ClientRegistrationException {
        return null;
    }
}
