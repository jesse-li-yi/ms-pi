package org.bcbs.microservice.organization.service.impl;

import org.bcbs.microservice.organization.dal.model.Clazz;
import org.bcbs.microservice.organization.dal.repository.ClazzRepository;
import org.bcbs.microservice.organization.service.ClazzService;
import org.bcbs.microservice.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ClazzServiceImpl extends GenericServiceImpl<Clazz, Integer, ClazzRepository> implements ClazzService {

    @Autowired
    public ClazzServiceImpl(ClazzRepository clazzRepository) {
        super(clazzRepository);
    }
}
