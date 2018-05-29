package org.bcbs.microservice.organization.controller;

import org.bcbs.microservice.controller.GenericController;
import org.bcbs.microservice.organization.dal.model.Student;
import org.bcbs.microservice.organization.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/student")
@ResponseBody
class StudentController extends GenericController<Student, Integer, StudentService> {

    @Autowired
    public StudentController(StudentService studentService) {
        super(studentService);
    }
}
