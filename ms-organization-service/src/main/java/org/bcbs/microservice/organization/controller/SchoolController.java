package org.bcbs.microservice.organization.controller;

import org.bcbs.microservice.controller.GenericController;
import org.bcbs.microservice.organization.dal.model.School;
import org.bcbs.microservice.organization.service.def.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/school")
@ResponseBody
class SchoolController extends GenericController<School, Integer, SchoolService> {

    @Autowired
    public SchoolController(SchoolService schoolService) {
        super(schoolService);
    }
}