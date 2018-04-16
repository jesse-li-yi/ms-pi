package org.bcbs.microservice.organization.service;

import org.bcbs.microservice.organization.dal.model.Parent;
import org.bcbs.microservice.organization.dal.repository.ParentRepository;
import org.bcbs.microservice.organization.service.def.ParentService;
import org.bcbs.microservice.service.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ParentServiceImpl extends GenericServiceImpl<Parent, Integer, ParentRepository> implements ParentService {

    @Autowired
    public ParentServiceImpl(ParentRepository parentRepository) {
        super(parentRepository);
    }
}
