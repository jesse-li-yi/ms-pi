package org.bcbs.microservice.oauth.resource;

import org.bcbs.microservice.oauth.patch.RedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private RedisTokenStore redisTokenStore;
    private ResourceAccessDeniedHandler resourceAccessDeniedHandler;

    @Autowired
    public ResourceServerConfig(RedisTokenStore redisTokenStore,
                                ResourceAccessDeniedHandler resourceAccessDeniedHandler) {
        this.redisTokenStore = redisTokenStore;
        this.resourceAccessDeniedHandler = resourceAccessDeniedHandler;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(this.redisTokenStore);//.accessDeniedHandler(resourceAccessDeniedHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/hystrix.stream").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        //.access("#oauth2.isClient() or hasAnyAuthority('app', 'admin')")
    }
}