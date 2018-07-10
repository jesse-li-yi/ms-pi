package org.bcbs.systemcore.lib.auth.config;

import org.bcbs.systemcore.lib.auth.provider.ResourceServerTokenService;
import org.bcbs.systemcore.lib.auth.provider.authentication.DefaultAuthenticationEntryPoint;
import org.bcbs.systemcore.lib.auth.provider.authentication.TokenAuthenticationFilter;
import org.bcbs.systemcore.lib.auth.provider.token.DefaultTokenService;
import org.bcbs.systemcore.lib.auth.provider.token.store.RedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@Order(value = 1)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    public ResourceServerSecurityConfiguration(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    private ResourceServerTokenService defaultTokenService() {
        return new DefaultTokenService(new RedisTokenStore(this.redisConnectionFactory));
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(defaultTokenService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .antMatcher("/**")
                .csrf().disable()
                .addFilterBefore(tokenAuthenticationFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new DefaultAuthenticationEntryPoint());
    }
}
