package org.bcbs.microservice.organization.service;

import org.bcbs.microservice.organization.dal.model.Clazz;
import org.bcbs.microservice.organization.dal.repository.ClazzRepository;
import org.bcbs.microservice.organization.service.def.ClazzService;
import org.bcbs.microservice.service.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClazzServiceImpl extends GenericServiceImpl<Clazz, Integer, ClazzRepository> implements ClazzService {

    @Autowired
    public ClazzServiceImpl(ClazzRepository clazzRepository) {
        super(clazzRepository);
    }
}
