package org.bcbs.microservice.organization.dal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bcbs.microservice.dal.model.AccountEntity;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.data.preset.Kinship;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "parent")
public class Parent extends GenericEntity<Integer> {

    @Embedded
    private AccountEntity account;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Kinship kinship;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "parent_student", joinColumns = {@JoinColumn(name = "parent_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Student> students;
}
