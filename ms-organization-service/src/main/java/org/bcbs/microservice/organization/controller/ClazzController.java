package org.bcbs.microservice.organization.controller;

import org.bcbs.microservice.controller.GenericController;
import org.bcbs.microservice.organization.dal.model.Clazz;
import org.bcbs.microservice.organization.service.def.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/class")
@ResponseBody
class ClazzController extends GenericController<Clazz, Integer, ClazzService> {

    @Autowired
    public ClazzController(ClazzService clazzService) {
        super(clazzService);
    }

    @GetMapping(value = "/create/{name}")
    public Clazz create(@PathVariable String name) {
        Clazz clazz = new Clazz();
        clazz.setName(name);
        return this.service.create(clazz);
    }

    @GetMapping(value = "/update/{id}/{name}")
    public Clazz update(@PathVariable Integer id, @PathVariable String name) {
        Clazz clazz = this.service.find(id);
        if (clazz != null) {
            clazz.setName(name);
            return this.service.update(clazz);
        } else
            return null;
    }

    @GetMapping(value = "/delete/{id}")
    public Clazz delete(@PathVariable Integer id) {
        return this.service.delete(id);
    }
}