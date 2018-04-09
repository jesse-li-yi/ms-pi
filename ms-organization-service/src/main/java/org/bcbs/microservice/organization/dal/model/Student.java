package org.bcbs.microservice.organization.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.dal.view.PublicDataView;
import org.bcbs.microservice.data.Gender;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "student")
public class Student extends GenericEntity<Integer> {

    @NotEmpty
    @Length(max = 16)
    @Column(length = 16, nullable = false)
    @JsonView(PublicDataView.class)
    private String sn;

    @NotEmpty
    @Length(max = 16)
    @Column(nullable = false)
    @JsonView(PublicDataView.class)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    @JsonView(PublicDataView.class)
    private Gender gender;

    @Length(max = 250)
    @Column(length = 250)
    @JsonView(PublicDataView.class)
    private String avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clazz_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView(PublicDataView.class)
    private Clazz clazz;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "students", cascade = {CascadeType.MERGE})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Parent> parents;
}
