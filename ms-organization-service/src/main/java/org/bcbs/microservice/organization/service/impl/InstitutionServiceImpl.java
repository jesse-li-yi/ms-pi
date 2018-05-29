package org.bcbs.microservice.organization.service.impl;

import org.bcbs.microservice.organization.dal.model.Institution;
import org.bcbs.microservice.organization.dal.repository.InstitutionRepository;
import org.bcbs.microservice.organization.service.InstitutionService;
import org.bcbs.microservice.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class InstitutionServiceImpl extends GenericServiceImpl<Institution, Integer, InstitutionRepository>
        implements InstitutionService {

    @Autowired
    public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
        super(institutionRepository);
    }
}
