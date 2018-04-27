package org.bcbs.microservice.organization.controller;

import org.bcbs.microservice.controller.GenericController;
import org.bcbs.microservice.organization.dal.model.President;
import org.bcbs.microservice.organization.service.def.PresidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/president")
@ResponseBody
class PresidentController extends GenericController<President, Integer, PresidentService> {

    @Autowired
    public PresidentController(PresidentService presidentService) {
        super(presidentService);
    }
}
