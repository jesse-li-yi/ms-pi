package org.bcbs.microservice.organization.service;

import org.bcbs.microservice.organization.dal.model.Student;
import org.bcbs.microservice.organization.dal.repository.StudentRepository;
import org.bcbs.microservice.organization.service.def.StudentService;
import org.bcbs.microservice.service.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class StudentServiceImpl extends GenericServiceImpl<Student, Integer, StudentRepository>
        implements StudentService {

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        super(studentRepository);
    }
}
