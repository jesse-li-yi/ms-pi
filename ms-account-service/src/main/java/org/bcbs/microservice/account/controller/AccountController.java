package org.bcbs.microservice.account.controller;

import org.bcbs.microservice.account.dal.model.Account;
import org.bcbs.microservice.account.service.AccountService;
import org.bcbs.microservice.controller.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/account")
@ResponseBody
class AccountController extends GenericController<Account, Integer, AccountService> {

    @Autowired
    public AccountController(AccountService accountService) {
        super(accountService);
    }
}
