package org.bcbs.microservice.organization.service.impl;

import org.bcbs.microservice.organization.dal.model.Parent;
import org.bcbs.microservice.organization.dal.repository.ParentRepository;
import org.bcbs.microservice.organization.service.ParentService;
import org.bcbs.microservice.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ParentServiceImpl extends GenericServiceImpl<Parent, Integer, ParentRepository>
        implements ParentService {

    @Autowired
    public ParentServiceImpl(ParentRepository parentRepository) {
        super(parentRepository);
    }
}
