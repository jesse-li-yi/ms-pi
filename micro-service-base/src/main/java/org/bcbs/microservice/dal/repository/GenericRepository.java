package org.bcbs.microservice.dal.repository;

import org.bcbs.microservice.dal.model.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface GenericRepository<T extends GenericEntity<I>, I extends Serializable>
        extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {
}
