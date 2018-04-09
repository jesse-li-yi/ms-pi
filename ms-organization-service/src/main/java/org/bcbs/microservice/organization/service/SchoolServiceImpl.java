package org.bcbs.microservice.organization.service;

import org.bcbs.microservice.organization.dal.model.School;
import org.bcbs.microservice.organization.dal.repository.SchoolRepository;
import org.bcbs.microservice.organization.service.def.SchoolService;
import org.bcbs.microservice.service.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl extends GenericServiceImpl<School, Integer, SchoolRepository>
        implements SchoolService {

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        super(schoolRepository);
    }
}
