package org.bcbs.microservice.organization.controller;

import org.bcbs.microservice.controller.GenericController;
import org.bcbs.microservice.organization.dal.model.Teacher;
import org.bcbs.microservice.organization.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/teacher")
@ResponseBody
class TeacherController extends GenericController<Teacher, Integer, TeacherService> {

    @Autowired
    public TeacherController(TeacherService teacherService) {
        super(teacherService);
    }
}
