package org.bcbs.microservice.organization.dal.model;

import org.bcbs.microservice.dal.model.AccountEntity;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "parent")
public class Parent extends GenericEntity<Integer> {

    @Embedded
    private AccountEntity account;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "parent_student", joinColumns = {@JoinColumn(name = "parent_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Student> students;

    // Getter & setter.
    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
