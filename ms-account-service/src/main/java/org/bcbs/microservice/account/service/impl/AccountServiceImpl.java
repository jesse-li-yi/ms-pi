package org.bcbs.microservice.account.service.impl;

import org.bcbs.microservice.account.dal.model.Account;
import org.bcbs.microservice.account.dal.repository.AccountRepository;
import org.bcbs.microservice.account.service.AccountService;
import org.bcbs.microservice.common.constraint.RegexPattern;
import org.bcbs.microservice.service.impl.GenericServiceImpl;
import org.bcbs.systemcore.lib.auth.provider.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
class AccountServiceImpl extends GenericServiceImpl<Account, Integer, AccountRepository>
        implements AccountService, AccountDetailsService {

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        super(accountRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User.UserBuilder builder = User.withUsername(name);

        if ("user".equalsIgnoreCase(name)) {
            builder.password("{bcrypt}$2a$10$qREoBZfkWW5toxJV2pHBf.0ZIHj1hVvU99A0XAvNaZF5DB20yyg/C").authorities("app");
        } else if ("admin".equalsIgnoreCase(name)) {
            builder.password("{bcrypt}$2a$10$qREoBZfkWW5toxJV2pHBf.0ZIHj1hVvU99A0XAvNaZF5DB20yyg/C").authorities("app", "admin", "internal");
        } else {
            throw new UsernameNotFoundException(name);
        }
        return builder.build();
    }

    @Override
    public UserDetails signup(String username, String password) throws BadCredentialsException {
        if (!(Pattern.compile(RegexPattern.PHONE_NO).matcher(username).matches()))
            throw new BadCredentialsException("Invalid phone number.");

        if (!(Pattern.compile(RegexPattern.STRONG_CIPHER).matcher(password).matches()))
            throw new BadCredentialsException("Invalid password.");

        Account account = new Account();
        account.setPhoneNo(username);
        account.setPassword(new BCryptPasswordEncoder().encode(password));
        account.setActive(true);
        //save(account);
        return new User(username, password, null);
    }
}
