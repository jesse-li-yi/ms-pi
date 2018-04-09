package org.bcbs.microservice.dal.repository;

import org.bcbs.microservice.dal.model.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GenericRepository<T extends GenericEntity<I>, I>
        extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {
}
