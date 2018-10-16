package org.bcbs.systemcore.lib.auth.provider.token;

import org.bcbs.systemcore.lib.auth.common.AccessToken;
import org.bcbs.systemcore.lib.auth.common.RefreshToken;
import org.bcbs.systemcore.lib.auth.common.exception.InvalidTokenException;
import org.bcbs.systemcore.lib.auth.common.exception.TokenGrantException;
import org.bcbs.systemcore.lib.auth.common.utility.CryptoHelper;
import org.bcbs.systemcore.lib.auth.provider.AuthServerTokenService;
import org.bcbs.systemcore.lib.auth.provider.ResourceServerTokenService;
import org.bcbs.systemcore.lib.auth.provider.TokenAuthentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

public class DefaultTokenService implements AuthServerTokenService, ResourceServerTokenService {

    private final TokenStore tokenStore;
    private final boolean reuseRefreshToken;

    public DefaultTokenService(TokenStore tokenStore) {
        this(tokenStore, false);
    }

    public DefaultTokenService(TokenStore tokenStore, boolean reuseRefreshToken) {
        this.tokenStore = tokenStore;
        this.reuseRefreshToken = reuseRefreshToken;
    }

    @Override
    @Transactional(rollbackFor = {TokenGrantException.class})
    public AccessToken createAccessToken(TokenAuthentication authentication) throws TokenGrantException {
        AccessToken accessToken = this.tokenStore.readAccessToken(authentication);
        if (accessToken == null) {
            Date now = Calendar.getInstance().getTime();
            accessToken = generateAccessToken(authentication, now);
            RefreshToken refreshToken = !this.reuseRefreshToken ? null :
                    this.tokenStore.readRefreshToken(authentication);

            if (refreshToken == null)
                refreshToken = generateRefreshToken(authentication, now);

            accessToken.setRefreshToken(refreshToken.getRefreshToken());
            refreshToken.setAccessToken(accessToken.getAccessToken());

            if (!this.tokenStore.storeToken(accessToken, refreshToken, authentication))
                throw new TokenGrantException();
        }
        return accessToken;
    }

    @Override
    @Transactional(rollbackFor = {TokenGrantException.class})
    public AccessToken refreshAccessToken(String tokenValue) throws InvalidTokenException, TokenGrantException {
        RefreshToken refreshToken = this.tokenStore.readRefreshToken(tokenValue);
        if (refreshToken == null)
            throw new InvalidTokenException();

        this.tokenStore.removeAccessToken(refreshToken.getAccessToken());

        TokenAuthentication tokenAuthentication = refreshToken.getTokenAuthentication();
        Date now = Calendar.getInstance().getTime();
        AccessToken accessToken = generateAccessToken(tokenAuthentication, now);

        if (!this.reuseRefreshToken) {
            this.tokenStore.removeRefreshToken(refreshToken.getRefreshToken());
            refreshToken = generateRefreshToken(tokenAuthentication, now);
        }

        accessToken.setRefreshToken(refreshToken.getRefreshToken());
        refreshToken.setAccessToken(accessToken.getAccessToken());

        if (!this.tokenStore.storeToken(accessToken, refreshToken, tokenAuthentication))
            throw new TokenGrantException();

        return accessToken;
    }

    @Override
    public AccessToken getAccessToken(String tokenValue) throws InvalidTokenException {
        AccessToken accessToken = this.tokenStore.readAccessToken(tokenValue);
        if (accessToken == null)
            throw new InvalidTokenException();
        return accessToken;
    }

    @Override
    public void revokeAccessToken(String tokenValue) throws InvalidTokenException {
        AccessToken accessToken = this.tokenStore.readAccessToken(tokenValue);
        if (accessToken == null)
            throw new InvalidTokenException();

        this.tokenStore.removeAccessToken(accessToken.getAccessToken());
        this.tokenStore.removeRefreshToken(accessToken.getRefreshToken());
    }

    @Override
    public void revokeUserAccessTokens(String username) {
    }

    @Override
    public TokenAuthentication loadAuthentication(String tokenValue) throws InvalidTokenException {
        AccessToken accessToken = this.tokenStore.readAccessToken(tokenValue);
        if (accessToken == null)
            throw new InvalidTokenException();
        return accessToken.getTokenAuthentication();
    }

    private AccessToken generateAccessToken(TokenAuthentication tokenAuthentication, Date issueTime) {
        Calendar expireTime = Calendar.getInstance();
        expireTime.setTime(issueTime);
        expireTime.add(Calendar.SECOND, tokenAuthentication.getAccessTokenTtlSeconds());

        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(CryptoHelper.generateTokenKey());
        accessToken.setTokenAuthentication(tokenAuthentication);
        accessToken.setIssueTime(issueTime);
        accessToken.setExpireTime(expireTime.getTime());
        return accessToken;
    }

    private RefreshToken generateRefreshToken(TokenAuthentication tokenAuthentication, Date issueTime) {
        Calendar expireTime = Calendar.getInstance();
        expireTime.setTime(issueTime);
        expireTime.add(Calendar.SECOND, tokenAuthentication.getRefreshTokenTtlSeconds());

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(CryptoHelper.generateTokenKey());
        refreshToken.setTokenAuthentication(tokenAuthentication);
        refreshToken.setIssueTime(issueTime);
        refreshToken.setExpireTime(expireTime.getTime());
        return refreshToken;
    }
}
