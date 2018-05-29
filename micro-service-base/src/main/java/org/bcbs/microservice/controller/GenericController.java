package org.bcbs.microservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.constraint.DataView;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.exception.DataNotFoundException;
import org.bcbs.microservice.service.GenericService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

public abstract class GenericController<T extends GenericEntity<N>, N extends Serializable,
        S extends GenericService<T, N>> {

    protected final S service;

    protected GenericController(S service) {
        this.service = service;
    }

    @GetMapping
    @JsonView(value = {DataView.TypicalView.class})
    public GenericResponse findAll() {
        return GenericResponse.success(this.service.findAll((root, cq, cb) -> cb.not(root.get("isDeleted")),
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"))));
    }

    @GetMapping(path = "/{id}")
    @JsonView(value = {DataView.TypicalView.class})
    public GenericResponse find(@PathVariable final N id) throws DataNotFoundException {
        return GenericResponse.success(this.service.findById(id));
    }

    @PostMapping
    @JsonView(value = {DataView.TypicalView.class})
    public GenericResponse create(@JsonView(value = {DataView.ExtensiveView.class}) @Validated @RequestBody T t) {
        t.setIsDeleted(false);
        return GenericResponse.success(this.service.save(t));
    }

    @PutMapping(path = "/{id}")
    @JsonView(value = {DataView.TypicalView.class})
    public GenericResponse update(@PathVariable final N id, @JsonView(value = {DataView.BasicView.class}) @RequestBody T t)
            throws DataNotFoundException {
        return GenericResponse.success(this.service.update(id, t));
    }

    @DeleteMapping(path = "/{id}")
    @JsonView(value = {DataView.TypicalView.class})
    public GenericResponse delete(@PathVariable final N id) throws DataNotFoundException {
        return GenericResponse.success(this.service.delete(id));
    }
}
