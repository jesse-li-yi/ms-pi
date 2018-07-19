package org.bcbs.microservice.account.controller;

import org.bcbs.microservice.account.dal.model.UserGroup;
import org.bcbs.microservice.account.service.UserGroupService;
import org.bcbs.microservice.controller.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user-group")
@ResponseBody
class UserGroupController extends GenericController<UserGroup, Integer, UserGroupService> {

    @Autowired
    public UserGroupController(UserGroupService userGroupService) {
        super(userGroupService);
    }
}
