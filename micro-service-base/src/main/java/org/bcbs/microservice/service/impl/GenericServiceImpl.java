package org.bcbs.microservice.service.impl;

import org.bcbs.microservice.common.exception.DataNotFoundException;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.dal.repository.GenericRepository;
import org.bcbs.microservice.dal.utility.EntityUtil;
import org.bcbs.microservice.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public abstract class GenericServiceImpl<T extends GenericEntity<N>, N extends Serializable,
        S extends GenericRepository<T, N>> implements GenericService<T, N> {

    protected final S repository;

    protected GenericServiceImpl(S repository) {
        this.repository = repository;
    }

    @Override
    public T save(@NonNull T t) {
        return this.repository.saveAndFlush(t);
    }

    @Override
    public T update(@NonNull N id, @NonNull T t) throws DataNotFoundException {
        T origin = this.repository.findById(id).orElseThrow(DataNotFoundException::new);
        EntityUtil.copyNonNullProperties(t, origin);
        return this.repository.saveAndFlush(origin);
    }

    @Override
    public T findById(@NonNull N id) throws DataNotFoundException {
        return this.repository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    @Override
    public T findOne(Specification<T> specification) throws DataNotFoundException {
        return this.repository.findOne(specification).orElseThrow(DataNotFoundException::new);
    }

    @Override
    public List<T> findAll() {
        return this.repository.findAll();
    }

    @Override
    public List<T> findAll(@Nullable Specification<T> specification, Sort sort) {
        return this.repository.findAll(specification, sort);
    }

    @Override
    public Page<T> findAll(@Nullable Specification<T> specification, Pageable pageable) {
        return this.repository.findAll(specification, pageable);
    }

    @Override
    public T delete(@NonNull N id) throws DataNotFoundException {
        T t = this.repository.findById(id).orElseThrow(DataNotFoundException::new);
        t.setDeleted(true);
        return this.repository.saveAndFlush(t);
    }

    @Override
    @Transactional
    public List<T> deleteAll(@NonNull List<N> ids) {
        List<T> ts = this.repository.findAllById(ids);
        if (!ts.isEmpty()) {
            for (T t : ts)
                t.setDeleted(true);
            ts = this.repository.saveAll(ts);
            this.repository.flush();
        }
        return ts;
    }
}
