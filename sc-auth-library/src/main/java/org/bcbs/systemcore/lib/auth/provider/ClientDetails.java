package org.bcbs.systemcore.lib.auth.provider;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public interface ClientDetails extends Serializable {

    String getClientId();

    String getClientSecret();

    Collection<GrantedAuthority> getScopes();

    int getAccessTokenTtlSeconds();

    int getRefreshTokenTtlSeconds();
}
