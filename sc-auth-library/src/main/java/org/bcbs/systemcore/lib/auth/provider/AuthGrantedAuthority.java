package org.bcbs.systemcore.lib.auth.provider;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class AuthGrantedAuthority implements GrantedAuthority {

    private String authority;

    public AuthGrantedAuthority() {
    }

    public AuthGrantedAuthority(String authority) {
        Assert.hasText(authority, "A granted authority textual representation is required!");
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj instanceof AuthGrantedAuthority)
            return authority.equals(((AuthGrantedAuthority) obj).authority);

        return false;
    }

    @Override
    public int hashCode() {
        return this.authority.hashCode();
    }
}
