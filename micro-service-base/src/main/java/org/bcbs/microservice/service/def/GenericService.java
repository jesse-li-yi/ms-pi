package org.bcbs.microservice.service.def;

import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T extends GenericEntity<I>, I extends Serializable> {

    T save(@NonNull T t);

    T update(@NonNull I id, @NonNull T t) throws DataNotFoundException;

    T findById(@NonNull I id) throws DataNotFoundException;

    T findOne(@Nullable Specification<T> specification) throws DataNotFoundException;

    List<T> findAll();

    List<T> findAll(@Nullable Specification<T> specification, Sort sort);

    Page<T> findAll(@Nullable Specification<T> specification, Pageable pageable);

    T delete(@NonNull I id) throws DataNotFoundException;

    List<T> deleteAll(@NonNull List<I> ids);
}
