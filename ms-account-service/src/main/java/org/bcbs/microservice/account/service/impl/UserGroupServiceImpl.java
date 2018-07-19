package org.bcbs.microservice.account.service.impl;

import org.bcbs.microservice.account.dal.model.UserGroup;
import org.bcbs.microservice.account.dal.repository.UserGroupRepository;
import org.bcbs.microservice.account.service.UserGroupService;
import org.bcbs.microservice.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserGroupServiceImpl extends GenericServiceImpl<UserGroup, Integer, UserGroupRepository>
        implements UserGroupService {

    @Autowired
    public UserGroupServiceImpl(UserGroupRepository userGroupRepository) {
        super(userGroupRepository);
    }
}
