package org.bcbs.systemcore.lib.auth.config;

import org.bcbs.systemcore.lib.auth.provider.AccountDetailsService;
import org.bcbs.systemcore.lib.auth.provider.ClientDetailsService;
import org.bcbs.systemcore.lib.auth.provider.authentication.ClientPreAuthenticationFilter;
import org.bcbs.systemcore.lib.auth.provider.authentication.DefaultAuthenticationEntryPoint;
import org.bcbs.systemcore.lib.auth.provider.client.ClientDetailsUserDetailsService;
import org.bcbs.systemcore.lib.auth.provider.client.DefaultClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@Order(value = 0)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class AuthServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private ClientDetailsService clientDetailsService;
    private AccountDetailsService accountDetailsService;

    @Autowired
    public AuthServerSecurityConfiguration(AccountDetailsService accountDetailsService) {
        this.clientDetailsService = new DefaultClientDetailsService();
        this.accountDetailsService = accountDetailsService;
    }

    @Bean
    public ClientDetailsService clientDetailsService() {
        return this.clientDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider clientAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(new ClientDetailsUserDetailsService(this.clientDetailsService));
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider accountAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.accountDetailsService);
        return provider;
    }

    @Bean
    public ClientPreAuthenticationFilter clientPreAuthenticationFilter() {
        return new ClientPreAuthenticationFilter(this.clientDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(true)
                .authenticationProvider(clientAuthenticationProvider())
                .authenticationProvider(accountAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .antMatcher("/auth/**")
                .csrf().disable()
                .addFilterBefore(clientPreAuthenticationFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new DefaultAuthenticationEntryPoint());
    }
}
