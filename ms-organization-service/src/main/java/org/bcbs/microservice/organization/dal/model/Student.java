package org.bcbs.microservice.organization.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.data.preset.Gender;
import org.bcbs.microservice.data.view.PublicView;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "student")
public class Student extends GenericEntity<Integer> {

    @NotEmpty(message = "Invalid sn of student.")
    @Length(max = 16)
    @Column(length = 16, nullable = false)
    @JsonView(PublicView.class)
    private String sn;

    @NotEmpty(message = "Invalid name of student.")
    @Length(max = 16)
    @Column(nullable = false)
    @JsonView(PublicView.class)
    private String name;

    @NotNull(message = "Invalid gender of student.")
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    @JsonView(PublicView.class)
    private Gender gender;

    @Length(max = 250)
    @Column(length = 250)
    @JsonView(PublicView.class)
    private String avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clazz_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView(PublicView.class)
    private Clazz clazz;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "students", cascade = {CascadeType.MERGE})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Parent> parents;
}
