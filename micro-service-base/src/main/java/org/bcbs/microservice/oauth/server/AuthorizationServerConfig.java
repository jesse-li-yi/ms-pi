package org.bcbs.microservice.oauth.server;

import org.bcbs.microservice.oauth.patch.RedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String[] AUTH_GRANT_TYPES = {"password", "refresh_token"};

    private AuthenticationManager authenticationManager;
    private AuthUserDetailsService authUserDetailsService;
    private RedisTokenStore redisTokenStore;

    @Autowired
    public AuthorizationServerConfig(AuthenticationManager authenticationManager,
                                     AuthUserDetailsService authUserDetailsService,
                                     RedisTokenStore redisTokenStore) {
        this.authenticationManager = authenticationManager;
        this.authUserDetailsService = authUserDetailsService;
        this.redisTokenStore = redisTokenStore;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("app")
                .secret("{noop}d2a57dc1d883fd21fb9951699df71cc7")
                .authorizedGrantTypes(AUTH_GRANT_TYPES)
                //.authorities("account, org, eval, exam")
                .scopes("read", "write")
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(43200)
                .and()
                .withClient("admin")
                .secret("{noop}21232f297a57a5a743894a0e4a801fc3")
                .authorizedGrantTypes(AUTH_GRANT_TYPES)
                //.authorities("app", "admin", "internal")
                .scopes("read", "write")
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(43200);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager)
                .tokenStore(this.redisTokenStore)
                .reuseRefreshTokens(false)
                .userDetailsService(this.authUserDetailsService);
                //.approvalStoreDisabled();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }
}