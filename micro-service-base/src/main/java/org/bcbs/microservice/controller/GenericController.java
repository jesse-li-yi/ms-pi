package org.bcbs.microservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.data.GenericResponse;
import org.bcbs.microservice.data.view.PublicView;
import org.bcbs.microservice.service.def.GenericService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

public abstract class GenericController<T extends GenericEntity<I>, I extends Serializable,
        S extends GenericService<T, I>> {

    protected final S service;

    protected GenericController(S service) {
        this.service = service;
    }

    // Find all.
    @GetMapping
    @JsonView(PublicView.class)
    public GenericResponse findAll() {
        return GenericResponse.success(this.service.findAll((root, cq, cb) -> cb.not(root.get("isDeleted")),
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"))));
    }

    // Find by id.
    @GetMapping(value = "/{id}")
    @JsonView(PublicView.class)
    public GenericResponse find(@PathVariable I id) {
        return GenericResponse.success(this.service.find(id));
    }

    // Create class.
    @PostMapping
    @JsonView(PublicView.class)
    public GenericResponse create(@JsonView(PublicView.class) @Validated @RequestBody T t) {
        return GenericResponse.success(this.service.create(t));
    }

    // Update class.
    // todo: only update content on demand
    @PutMapping(value = "/{id}")
    @JsonView(PublicView.class)
    public GenericResponse update(@PathVariable I id, @JsonView(PublicView.class) @Validated @RequestBody T t) {
        t.setId(id);
        return GenericResponse.success(this.service.update(t));
    }

    // Delete class by id.
    @DeleteMapping(value = "/{id}")
    @JsonView(PublicView.class)
    public GenericResponse delete(@PathVariable I id) {
        return GenericResponse.success(this.service.delete(id));
    }
}
