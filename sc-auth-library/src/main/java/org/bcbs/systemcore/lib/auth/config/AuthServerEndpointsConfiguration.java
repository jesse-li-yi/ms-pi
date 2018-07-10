package org.bcbs.systemcore.lib.auth.config;

import org.bcbs.systemcore.lib.auth.provider.AuthServerTokenService;
import org.bcbs.systemcore.lib.auth.provider.endpoint.AuthEndpointHandlerMapping;
import org.bcbs.systemcore.lib.auth.provider.endpoint.TokenEndpoint;
import org.bcbs.systemcore.lib.auth.provider.token.DefaultTokenService;
import org.bcbs.systemcore.lib.auth.provider.token.TokenGranter;
import org.bcbs.systemcore.lib.auth.provider.token.granter.ClientCredentialsTokenGranter;
import org.bcbs.systemcore.lib.auth.provider.token.granter.CompositeTokenGranter;
import org.bcbs.systemcore.lib.auth.provider.token.granter.PasswordTokenGranter;
import org.bcbs.systemcore.lib.auth.provider.token.granter.RefreshTokenGranter;
import org.bcbs.systemcore.lib.auth.provider.token.store.RedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Collection;
import java.util.HashSet;

@Configuration
@Import(value = {TokenEndpoint.class})
public class AuthServerEndpointsConfiguration {

    private AuthenticationManager authenticationManager;
    private RedisConnectionFactory redisConnectionFactory;
    private AuthServerTokenService authServerTokenService;

    @Autowired
    public AuthServerEndpointsConfiguration(AuthenticationManager authenticationManager,
                                            RedisConnectionFactory redisConnectionFactory) {
        this.authenticationManager = authenticationManager;
        this.redisConnectionFactory = redisConnectionFactory;
        this.authServerTokenService = new DefaultTokenService(
                new RedisTokenStore(this.redisConnectionFactory), false);
    }

    @Bean
    public AuthEndpointHandlerMapping authEndpointHandlerMapping() {
        return new AuthEndpointHandlerMapping();
    }

    @Bean
    public TokenGranter tokenGranter() {
        return new CompositeTokenGranter(getDefaultTokenGranters());
    }

    @Bean
    public AuthServerTokenService authServerTokenService() {
        return this.authServerTokenService;
    }

    private Collection<TokenGranter> getDefaultTokenGranters() {
        HashSet<TokenGranter> tokenGranters = new HashSet<>();
        tokenGranters.add(new ClientCredentialsTokenGranter(this.authServerTokenService));
        tokenGranters.add(new PasswordTokenGranter(this.authenticationManager, this.authServerTokenService));
        tokenGranters.add(new RefreshTokenGranter(this.authServerTokenService));
        return tokenGranters;
    }
}
