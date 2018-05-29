package org.bcbs.microservice.organization.service.impl;

import org.bcbs.microservice.organization.dal.model.President;
import org.bcbs.microservice.organization.dal.repository.PresidentRepository;
import org.bcbs.microservice.organization.service.PresidentService;
import org.bcbs.microservice.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class PresidentServiceImpl extends GenericServiceImpl<President, Integer, PresidentRepository>
        implements PresidentService {

    @Autowired
    public PresidentServiceImpl(PresidentRepository presidentRepository) {
        super(presidentRepository);
    }
}
