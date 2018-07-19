package org.bcbs.microservice.account.controller;

import org.bcbs.microservice.account.dal.model.UserPrivilege;
import org.bcbs.microservice.account.service.UserPrivilegeService;
import org.bcbs.microservice.controller.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user-privilege")
@ResponseBody
class UserPrivilegeController extends GenericController<UserPrivilege, Integer, UserPrivilegeService> {

    @Autowired
    public UserPrivilegeController(UserPrivilegeService userPrivilegeService) {
        super(userPrivilegeService);
    }
}
