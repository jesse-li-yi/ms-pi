package org.bcbs.microservice.account.service.impl;

import org.bcbs.microservice.account.dal.model.UserPrivilege;
import org.bcbs.microservice.account.dal.repository.UserPrivilegeRepository;
import org.bcbs.microservice.account.service.UserPrivilegeService;
import org.bcbs.microservice.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserPrivilegeServiceImpl extends GenericServiceImpl<UserPrivilege, Integer, UserPrivilegeRepository>
        implements UserPrivilegeService {

    @Autowired
    public UserPrivilegeServiceImpl(UserPrivilegeRepository userPrivilegeRepository) {
        super(userPrivilegeRepository);
    }
}
