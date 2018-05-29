package org.bcbs.microservice.organization.service.impl;

import org.bcbs.microservice.organization.dal.model.School;
import org.bcbs.microservice.organization.dal.repository.SchoolRepository;
import org.bcbs.microservice.organization.service.SchoolService;
import org.bcbs.microservice.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class SchoolServiceImpl extends GenericServiceImpl<School, Integer, SchoolRepository> implements SchoolService {

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        super(schoolRepository);
    }
}
