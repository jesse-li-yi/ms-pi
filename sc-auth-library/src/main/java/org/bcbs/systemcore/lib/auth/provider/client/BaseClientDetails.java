package org.bcbs.systemcore.lib.auth.provider.client;

import org.bcbs.systemcore.lib.auth.provider.ClientDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

class BaseClientDetails implements ClientDetails {

    private static final int DEFAULT_ACCESS_TOKEN_TTL = 7200;
    private static final int DEFAULT_REFRESH_TOKEN_TTL = 43200;

    private String clientId;
    private String clientSecret;
    private Collection<GrantedAuthority> scopes;
    private int accessTokenTtlSeconds = DEFAULT_ACCESS_TOKEN_TTL;
    private int refreshTokenTtlSeconds = DEFAULT_REFRESH_TOKEN_TTL;

    BaseClientDetails(String clientId, String clientSecret, Collection<GrantedAuthority> scopes) {
        Assert.hasText(clientId, "ClientId cannot be null!");
        Assert.hasText(clientSecret, "ClientSecret cannot be null!");
        this.clientId = clientId.trim();
        this.clientSecret = clientSecret.trim();
        setScopes(scopes);
    }

    // Getter & setter.
    @Override
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public Collection<GrantedAuthority> getScopes() {
        return scopes;
    }

    public void setScopes(Collection<GrantedAuthority> scopes) {
        this.scopes = scopes == null ? Collections.emptySet() :
                Collections.unmodifiableCollection(new LinkedHashSet<>(scopes));
    }

    @Override
    public int getAccessTokenTtlSeconds() {
        return accessTokenTtlSeconds;
    }

    public void setAccessTokenTtlSeconds(int accessTokenTtlSeconds) {
        this.accessTokenTtlSeconds = accessTokenTtlSeconds;
    }

    @Override
    public int getRefreshTokenTtlSeconds() {
        return refreshTokenTtlSeconds;
    }

    public void setRefreshTokenTtlSeconds(int refreshTokenTtlSeconds) {
        this.refreshTokenTtlSeconds = refreshTokenTtlSeconds;
    }
}
