package org.bcbs.microservice.organization.service.impl;

import org.bcbs.microservice.organization.dal.model.Student;
import org.bcbs.microservice.organization.dal.repository.StudentRepository;
import org.bcbs.microservice.organization.service.StudentService;
import org.bcbs.microservice.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class StudentServiceImpl extends GenericServiceImpl<Student, Integer, StudentRepository> implements StudentService {

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        super(studentRepository);
    }
}
