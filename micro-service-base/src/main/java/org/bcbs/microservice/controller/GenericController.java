package org.bcbs.microservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.dal.view.PublicDataView;
import org.bcbs.microservice.service.def.GenericService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericController<T extends GenericEntity<I>, I, S extends GenericService<T, I>> {

    protected final S service;

    public GenericController(S service) {
        this.service = service;
    }

    // Find all.
    @GetMapping
    @JsonView(PublicDataView.class)
    public List<T> findAll() {
        return this.service.findAll();
        /*return this.service.findAll((root, cq, cb) -> cb.not(root.get("isDeleted")),
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")));*/
    }

    // Find by id.
    @GetMapping(value = "/{id}")
    @JsonView(PublicDataView.class)
    public T find(@PathVariable I id) {
        return this.service.find(id);
    }

    // Create class.
    @PostMapping
    public T create(@Validated @RequestBody T t) {
        return this.service.create(t);
    }

    // Update class.
    // todo: only update content on demand
    @PutMapping()
    public T update(@Validated @RequestBody T t) {
        return this.service.update(t);
    }

    // Delete class by id.
    @DeleteMapping(value = "/{id}")
    public T delete(@PathVariable I id) {
        return this.service.delete(id);
    }
}
