package org.bcbs.systemcore.lib.auth.provider.client;

import org.bcbs.systemcore.lib.auth.common.exception.NoSuchClientException;
import org.bcbs.systemcore.lib.auth.provider.ClientDetails;
import org.bcbs.systemcore.lib.auth.provider.ClientDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ClientDetailsUserDetailsService implements UserDetailsService {

    private ClientDetailsService clientDetailsService;

    public ClientDetailsUserDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);

            return new User(username, clientDetails.getClientSecret().trim(), clientDetails.getScopes());
        } catch (NoSuchClientException ex) {
            throw new UsernameNotFoundException(ex.getMessage(), ex);
        }
    }
}
