package org.bcbs.systemcore.lib.auth.provider;

import org.bcbs.systemcore.lib.auth.common.constraint.GrantType;
import org.bcbs.systemcore.lib.auth.common.utility.CryptoHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

public class TokenAuthentication {

    private static final String AUTHORITY_DELIMITER = ",";

    private String authKey;
    private GrantType grantType;
    private String clientId;
    private String username;
    private String authorities;
    private int accessTokenTtlSeconds = 0;
    private int refreshTokenTtlSeconds = 0;

    public TokenAuthentication() {
    }

    public TokenAuthentication(GrantType grantType, ClientDetails clientDetails, Authentication userAuthentication) {
        Assert.isTrue(GrantType.CLIENT_CREDENTIALS.equals(grantType) || GrantType.PASSWORD.equals(grantType),
                "GrantType can only be CLIENT_CREDENTIALS or PASSWORD!");
        Assert.notNull(clientDetails, "ClientDetails cannot be null!");
        Assert.isTrue(!GrantType.PASSWORD.equals(grantType) || userAuthentication != null,
                "UserAuthentication cannot be null for username/password authentication!");

        this.grantType = grantType;
        this.clientId = clientDetails.getClientId();
        this.accessTokenTtlSeconds = clientDetails.getAccessTokenTtlSeconds();
        this.refreshTokenTtlSeconds = clientDetails.getRefreshTokenTtlSeconds();

        if (GrantType.PASSWORD.equals(grantType)) {
            this.username = userAuthentication.getName();
            setAuthorities(userAuthentication.getAuthorities());
        } else {
            this.username = this.clientId;
            setAuthorities(clientDetails.getScopes());
        }

        this.authKey = CryptoHelper.md5Encode(String.format("%s:%s", this.clientId, this.username));
    }

    public PreAuthenticatedAuthenticationToken toAuthentication() {
        LinkedHashSet<GrantedAuthority> grantedAuthorities = null;
        if (!StringUtils.isEmpty(this.authorities)) {
            grantedAuthorities = new LinkedHashSet<>();
            for (String authority : this.authorities.split(AUTHORITY_DELIMITER))
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.toLowerCase()));
        }
        return new PreAuthenticatedAuthenticationToken(this.username, null, grantedAuthorities);
    }

    // Getter & setter.
    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public GrantType getGrantType() {
        return grantType;
    }

    public void setGrantType(GrantType grantType) {
        this.grantType = grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthorities() {
        return authorities;
    }

    private void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        ArrayList<String> grantedAuthorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(authorities))
            for (GrantedAuthority authority : authorities)
                grantedAuthorities.add(authority.getAuthority());

        this.authorities = grantedAuthorities.isEmpty() ? null : String.join(AUTHORITY_DELIMITER, grantedAuthorities);
    }

    public void setAuthorities(String authorities) {
        this.authorities = StringUtils.isEmpty(authorities) ? null : authorities;
    }

    public int getAccessTokenTtlSeconds() {
        return accessTokenTtlSeconds;
    }

    public void setAccessTokenTtlSeconds(int accessTokenTtlSeconds) {
        this.accessTokenTtlSeconds = accessTokenTtlSeconds;
    }

    public int getRefreshTokenTtlSeconds() {
        return refreshTokenTtlSeconds;
    }

    public void setRefreshTokenTtlSeconds(int refreshTokenTtlSeconds) {
        this.refreshTokenTtlSeconds = refreshTokenTtlSeconds;
    }
}
