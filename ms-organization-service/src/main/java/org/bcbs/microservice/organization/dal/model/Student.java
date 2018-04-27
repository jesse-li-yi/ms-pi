package org.bcbs.microservice.organization.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.constant.DataView;
import org.bcbs.microservice.constant.Gender;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student extends GenericEntity<Integer> {

    @NotEmpty(message = "Invalid sn of student.")
    @Length(max = 16)
    @Column(length = 16, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String sn;

    @NotEmpty(message = "Invalid name of student.")
    @Length(max = 16)
    @Column(nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String name;

    @NotNull(message = "Invalid gender of student.")
    @Enumerated(value = EnumType.STRING)
    @Column(length = 10, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private Gender gender;

    @Length(max = 250)
    @Column(length = 250)
    @JsonView(value = {DataView.BasicView.class})
    private String avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView(value = {DataView.TypicalView.class})
    private Clazz clazz;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "students", cascade = {CascadeType.REFRESH})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Parent> parents;
}
