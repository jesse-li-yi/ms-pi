package org.bcbs.microservice.account.service.impl;

import org.bcbs.microservice.account.dal.model.Account;
import org.bcbs.microservice.account.dal.repository.AccountRepository;
import org.bcbs.microservice.account.service.AccountService;
import org.bcbs.microservice.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class AccountServiceImpl extends GenericServiceImpl<Account, Integer, AccountRepository>
        implements AccountService {

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        super(accountRepository);
    }
}
