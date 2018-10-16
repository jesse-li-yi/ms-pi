package org.bcbs.systemcore.lib.auth.provider;

import org.bcbs.systemcore.lib.auth.common.exception.SignupException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountDetailsService extends UserDetailsService {

    UserDetails signup(String name, String password) throws SignupException;
}
