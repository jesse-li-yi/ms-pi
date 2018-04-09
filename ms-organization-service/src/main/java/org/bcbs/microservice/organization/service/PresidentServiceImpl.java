package org.bcbs.microservice.organization.service;

import org.bcbs.microservice.organization.dal.model.President;
import org.bcbs.microservice.organization.dal.repository.PresidentRepository;
import org.bcbs.microservice.organization.service.def.PresidentService;
import org.bcbs.microservice.service.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresidentServiceImpl extends GenericServiceImpl<President, Integer, PresidentRepository>
        implements PresidentService {

    @Autowired
    public PresidentServiceImpl(PresidentRepository presidentRepository) {
        super(presidentRepository);
    }
}
