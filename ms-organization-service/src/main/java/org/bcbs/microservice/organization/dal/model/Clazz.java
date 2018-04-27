package org.bcbs.microservice.organization.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.constant.DataView;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "clazz")
public class Clazz extends GenericEntity<Integer> {

    @NotEmpty(message = "Invalid name of class.")
    @Length(max = 32)
    @Column(length = 32, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView(value = {DataView.TypicalView.class})
    private School school;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clazz")
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Student> students;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "clazzes", cascade = {CascadeType.REFRESH})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Teacher> teachers;
}
