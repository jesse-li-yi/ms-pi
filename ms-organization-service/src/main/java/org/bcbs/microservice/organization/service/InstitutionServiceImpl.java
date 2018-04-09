package org.bcbs.microservice.organization.service;

import org.bcbs.microservice.organization.dal.model.Institution;
import org.bcbs.microservice.organization.dal.repository.InstitutionRepository;
import org.bcbs.microservice.organization.service.def.InstitutionService;
import org.bcbs.microservice.service.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstitutionServiceImpl extends GenericServiceImpl<Institution, Integer, InstitutionRepository>
        implements InstitutionService {

    @Autowired
    public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
        super(institutionRepository);
    }
}
