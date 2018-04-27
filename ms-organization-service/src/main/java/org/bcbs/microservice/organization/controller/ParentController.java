package org.bcbs.microservice.organization.controller;

import org.bcbs.microservice.controller.GenericController;
import org.bcbs.microservice.organization.dal.model.Parent;
import org.bcbs.microservice.organization.service.def.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/parent")
@ResponseBody
class ParentController extends GenericController<Parent, Integer, ParentService> {

    @Autowired
    public ParentController(ParentService parentService) {
        super(parentService);
    }
}
