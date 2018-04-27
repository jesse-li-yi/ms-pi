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
@Table(name = "school")
public class School extends GenericEntity<Integer> {

    @NotEmpty(message = "Invalid sn of school.")
    @Length(max = 16)
    @Column(length = 16, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String sn;

    @NotEmpty(message = "Invalid name of school.")
    @Length(max = 32)
    @Column(length = 32, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView(value = {DataView.TypicalView.class})
    private Institution institution;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "schools", cascade = {CascadeType.REFRESH})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<President> presidents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "school")
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Clazz> clazzes;
}
