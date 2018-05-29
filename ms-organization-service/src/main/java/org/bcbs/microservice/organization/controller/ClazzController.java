package org.bcbs.microservice.organization.controller;

import org.bcbs.microservice.controller.GenericController;
import org.bcbs.microservice.organization.dal.model.Clazz;
import org.bcbs.microservice.organization.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/class")
@ResponseBody
class ClazzController extends GenericController<Clazz, Integer, ClazzService> {

    @Autowired
    public ClazzController(ClazzService clazzService) {
        super(clazzService);
    }
}
