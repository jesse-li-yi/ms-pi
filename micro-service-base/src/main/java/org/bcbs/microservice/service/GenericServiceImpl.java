package org.bcbs.microservice.service;

import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.dal.repository.GenericRepository;
import org.bcbs.microservice.service.def.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class GenericServiceImpl<T extends GenericEntity<I>, I, R extends GenericRepository<T, I>>
        implements GenericService<T, I> {

    protected final R repository;

    public GenericServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public T create(@NonNull T t) {
        t.setId(null);
        return this.repository.saveAndFlush(t);
    }

    @Override
    public T update(@NonNull T t) {
        return this.repository.existsById(t.getId()) ? this.repository.saveAndFlush(t) : null;
    }

    @Override
    public T find(@NonNull I id) {
        return this.repository.findById(id).orElse(null);
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
    public T delete(@NonNull I id) {
        T t = this.repository.findById(id).orElse(null);
        if (t != null) {
            t.setIsDeleted(true);
            t = this.repository.saveAndFlush(t);
        }
        return t;
    }

    @Override
    @Transactional
    public List<T> deleteAll(@NonNull List<I> ids) {
        List<T> ts = this.repository.findAllById(ids);
        if (!ts.isEmpty()) {
            for (T t : ts)
                t.setIsDeleted(true);
            ts = this.repository.saveAll(ts);
            this.repository.flush();
        }
        return ts;
    }
}
