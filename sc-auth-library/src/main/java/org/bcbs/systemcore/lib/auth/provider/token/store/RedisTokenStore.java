package org.bcbs.systemcore.lib.auth.provider.token.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.bcbs.systemcore.lib.auth.common.AccessToken;
import org.bcbs.systemcore.lib.auth.common.RefreshToken;
import org.bcbs.systemcore.lib.auth.provider.TokenAuthentication;
import org.bcbs.systemcore.lib.auth.provider.token.TokenStore;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import java.io.IOException;

public class RedisTokenStore implements TokenStore {

    private static final String AUTH_TO_ACCESS = "auth_to_access:";
    private static final String AUTH_TO_REFRESH = "auth_to_refresh:";
    private static final String ACCESS = "access:";
    private static final String ACCESS_TO_REFRESH = "access_to_refresh:";
    private static final String REFRESH = "refresh:";
    private static final String REFRESH_TO_ACCESS = "refresh_to_access:";

    private StringRedisTemplate stringRedisTemplate;
    private ObjectMapper objectMapper;

    public RedisTokenStore(RedisConnectionFactory redisConnectionFactory) {
        this.stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @Override
    public boolean storeToken(AccessToken accessToken, RefreshToken refreshToken, TokenAuthentication auth) {
        Assert.notNull(accessToken, "AccessToken cannot be null!");
        Assert.notNull(refreshToken, "RefreshToken cannot be null!");
        Assert.notNull(auth, "TokenAuthentication cannot be null!");

        try {
            String key = AUTH_TO_ACCESS + auth.getAuthKey();
            this.stringRedisTemplate.boundValueOps(key).set(accessToken.getAccessToken());
            this.stringRedisTemplate.expireAt(key, accessToken.getExpireTime());

            key = AUTH_TO_REFRESH + auth.getAuthKey();
            this.stringRedisTemplate.boundValueOps(key).set(refreshToken.getRefreshToken());
            this.stringRedisTemplate.expireAt(key, refreshToken.getExpireTime());

            key = ACCESS + accessToken.getAccessToken();
            this.stringRedisTemplate.boundValueOps(key).set(serializeToken(accessToken));
            this.stringRedisTemplate.expireAt(key, accessToken.getExpireTime());

            key = ACCESS_TO_REFRESH + accessToken.getAccessToken();
            this.stringRedisTemplate.boundValueOps(key).set(accessToken.getRefreshToken());
            this.stringRedisTemplate.expireAt(key, accessToken.getExpireTime());

            key = REFRESH + refreshToken.getRefreshToken();
            this.stringRedisTemplate.boundValueOps(key).set(serializeToken(refreshToken));
            this.stringRedisTemplate.expireAt(key, refreshToken.getExpireTime());

            key = REFRESH_TO_ACCESS + refreshToken.getRefreshToken();
            this.stringRedisTemplate.boundValueOps(key).set(refreshToken.getAccessToken());
            this.stringRedisTemplate.expireAt(key, refreshToken.getExpireTime());

            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public AccessToken readAccessToken(TokenAuthentication auth) {
        return readAccessToken(this.stringRedisTemplate.boundValueOps(AUTH_TO_ACCESS + auth.getAuthKey()).get());
    }

    @Override
    public AccessToken readAccessToken(String tokenValue) {
        try {
            return deserializeToken(this.stringRedisTemplate.boundValueOps(ACCESS + tokenValue).get(),
                    AccessToken.class);
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public void removeAccessToken(String tokenValue) {
        this.stringRedisTemplate.delete(ACCESS + tokenValue);
        this.stringRedisTemplate.delete(ACCESS_TO_REFRESH + tokenValue);
    }

    @Override
    public RefreshToken readRefreshToken(TokenAuthentication auth) {
        return readRefreshToken(this.stringRedisTemplate.boundValueOps(AUTH_TO_REFRESH + auth.getAuthKey()).get());
    }

    @Override
    public RefreshToken readRefreshToken(String tokenValue) {
        try {
            return deserializeToken(this.stringRedisTemplate.boundValueOps(REFRESH + tokenValue).get(),
                    RefreshToken.class);
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public void removeRefreshToken(String tokenValue) {
        this.stringRedisTemplate.delete(REFRESH + tokenValue);
        this.stringRedisTemplate.delete(REFRESH_TO_ACCESS + tokenValue);
    }

    private <T> String serializeToken(T token) throws IOException {
        return token == null ? null : this.objectMapper.writeValueAsString(token);
    }

    private <T> T deserializeToken(String data, Class<T> tokenType) throws IOException {
        return (data == null || tokenType == null) ? null : this.objectMapper.readValue(data, tokenType);
    }
}
