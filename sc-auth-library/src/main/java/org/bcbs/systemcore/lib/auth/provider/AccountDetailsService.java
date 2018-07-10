package org.bcbs.systemcore.lib.auth.provider;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountDetailsService extends UserDetailsService {

    UserDetails signup(String username, String password) throws BadCredentialsException;
}
