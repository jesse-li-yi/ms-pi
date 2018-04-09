package org.bcbs.microservice.service.def;

import org.bcbs.microservice.dal.model.GenericEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface GenericService<T extends GenericEntity<I>, I> {

    T create(@NonNull T t);

    T update(@NonNull T t);

    T find(@NonNull I id);

    List<T> findAll();

    List<T> findAll(@Nullable Specification<T> specification, Sort sort);

    Page<T> findAll(@Nullable Specification<T> specification, Pageable pageable);

    T delete(@NonNull I id);

    List<T> deleteAll(@NonNull List<I> ids);
}
