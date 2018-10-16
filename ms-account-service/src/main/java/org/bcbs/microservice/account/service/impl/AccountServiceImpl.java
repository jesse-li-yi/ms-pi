package org.bcbs.microservice.account.service.impl;

import org.bcbs.microservice.account.dal.model.Account;
import org.bcbs.microservice.account.dal.model.Account_;
import org.bcbs.microservice.account.dal.repository.AccountRepository;
import org.bcbs.microservice.account.service.AccountService;
import org.bcbs.microservice.common.constraint.RegexPattern;
import org.bcbs.microservice.service.impl.GenericServiceImpl;
import org.bcbs.systemcore.lib.auth.common.exception.SignupException;
import org.bcbs.systemcore.lib.auth.provider.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDetails signup(String name, String password) throws SignupException {
        if (!(Pattern.compile(RegexPattern.PHONE_NO).matcher(name).matches()))
            throw new SignupException("Invalid phone number.");

        if (this.repository.count((root, cq, cb) -> cb.equal(root.get(Account_.phoneNo), name)) > 0)
            throw new SignupException("The phone number has already been registered.");

        if (!(Pattern.compile(RegexPattern.STRONG_CIPHER).matcher(password).matches()))
            throw new SignupException("Invalid password.");

        Account account = new Account();
        account.setPhoneNo(name);
        account.setPassword(new BCryptPasswordEncoder().encode(password));
        account.setActive(true);
        save(account);
        return account.toUserDetails();
    }
}
