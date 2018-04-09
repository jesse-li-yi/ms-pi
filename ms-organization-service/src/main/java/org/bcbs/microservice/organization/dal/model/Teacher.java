package org.bcbs.microservice.organization.dal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bcbs.microservice.dal.model.Account;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "teacher")
public class Teacher extends GenericEntity<Integer> {

    @Embedded
    private Account account;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "teacher_clazz", joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "clazz_id")})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Clazz> clazzes;
}