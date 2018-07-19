package org.bcbs.microservice.account.service.impl;

import org.bcbs.microservice.account.dal.model.UserRole;
import org.bcbs.microservice.account.dal.repository.UserRoleRepository;
import org.bcbs.microservice.account.service.UserRoleService;
import org.bcbs.microservice.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserRoleServiceImpl extends GenericServiceImpl<UserRole, Integer, UserRoleRepository>
        implements UserRoleService {

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        super(userRoleRepository);
    }
}
