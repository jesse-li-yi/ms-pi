package org.bcbs.microservice.account.controller;

import org.bcbs.microservice.account.dal.model.UserRole;
import org.bcbs.microservice.account.service.UserRoleService;
import org.bcbs.microservice.controller.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user-role")
@ResponseBody
class UserRoleController extends GenericController<UserRole, Integer, UserRoleService> {

    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        super(userRoleService);
    }
}
