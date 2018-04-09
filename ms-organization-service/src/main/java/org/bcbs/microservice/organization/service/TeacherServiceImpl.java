package org.bcbs.microservice.organization.service;

import org.bcbs.microservice.organization.dal.model.Teacher;
import org.bcbs.microservice.organization.dal.repository.TeacherRepository;
import org.bcbs.microservice.organization.service.def.TeacherService;
import org.bcbs.microservice.service.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends GenericServiceImpl<Teacher, Integer, TeacherRepository>
        implements TeacherService {

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        super(teacherRepository);
    }
}
