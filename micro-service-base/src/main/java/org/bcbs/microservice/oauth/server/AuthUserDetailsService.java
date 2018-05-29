package org.bcbs.microservice.oauth.server;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
class AuthUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User.UserBuilder builder = User.withUsername(name);

        if ("user".equalsIgnoreCase(name)) {
            builder.password("{noop}password").roles("USER");//.authorities("app");
        } else if ("admin".equalsIgnoreCase(name)) {
            builder.password("{noop}password").roles("USER", "ADMIN");//.authorities("app", "admin", "internal");
        } else {
            throw new UsernameNotFoundException(name);
        }
        return builder.build();
    }
}
